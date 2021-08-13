package org.gfa.greenbay.security;

import java.util.List;
import org.gfa.greenbay.models.User;
import org.springframework.security.core.GrantedAuthority;

public interface JwtUtilService {

  String generateToken(User userData);

  boolean validateToken(String authToken);

  String getUsernameFromToken(String token);

  List<GrantedAuthority> getAuthoritiesFromToken(String token);

}
