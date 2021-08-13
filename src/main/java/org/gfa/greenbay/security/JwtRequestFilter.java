package org.gfa.greenbay.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JwtUtilService jwtUtilService;

  @Autowired
  public JwtRequestFilter(
      UserDetailsServiceImpl userDetailsService,
      JwtUtilService jwtUtilService) {
    this.userDetailsService = userDetailsService;
    this.jwtUtilService = jwtUtilService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {

    String jwt = getToken(request);

    if (jwt != null
        && SecurityContextHolder.getContext().getAuthentication() == null
        && jwtUtilService.validateToken(jwt)) {
      authenticateUser(jwt, request);
    }
    chain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

  private void authenticateUser(String jwt, HttpServletRequest request) {
    String username = jwtUtilService.getUsernameFromToken(jwt);
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken
        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

}