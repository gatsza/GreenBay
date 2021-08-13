package org.gfa.greenbay.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.gfa.greenbay.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtUtilServiceImpl implements JwtUtilService {

  private String secret;
  private int jwtExpirationInMs;

  @Value("${jwt.secret}")
  public void setSecret(String secret) {
    this.secret = secret;
  }

  @Value("${jwt.expirationDateInMs}")
  public void setJwtExpirationInMs(int jwtExpirationInMs) {
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  @Override
  public String generateToken(User userData) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userData.getRoles());
    return buildToken(claims, userData);
  }

  private String buildToken(Map<String, Object> claims, User userData) {
    return Jwts.builder().setClaims(claims).setSubject(userData.getUsername())
        .setId(userData.getId().toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  public boolean validateToken(String authToken) {
    try {
      Jws<Claims> claims = getParsedJwt(authToken);
      return true;
    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
    } catch (ExpiredJwtException ex) {
      throw ex;
    }
  }

  private Jws<Claims> getParsedJwt(String authToken) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
  }

  public String getUsernameFromToken(String token) {
    Claims claims = getParsedJwt(token).getBody();
    return claims.getSubject();
  }

  public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
    String roles = getParsedJwt(token).getBody().get("roles", String.class);

    List<GrantedAuthority> authorities = parseAuthoritiesToList(roles);

    if (authorities.size() == 0) {
      return null;
    }
    return authorities;
  }

  private List<GrantedAuthority> parseAuthoritiesToList(String roles) {
    if (roles == null) {
      return null;
    }
    return Arrays.stream(roles.split(","))
        .filter(Objects::nonNull)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}