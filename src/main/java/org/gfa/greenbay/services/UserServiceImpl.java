package org.gfa.greenbay.services;

import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.UserRepository;
import org.gfa.greenbay.security.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final JwtUtilService jwtUtilService;
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      JwtUtilService jwtUtilService) {
    this.userRepository = userRepository;
    this.jwtUtilService = jwtUtilService;
  }

  @Override
  public String generateToken(String username) {
    User loginUser = userRepository.findUserByUsername(username).get();
    return jwtUtilService.generateToken(loginUser);
  }


}
