package org.gfa.greenbay.services;

import java.util.Optional;
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
    User loginUser = findUserByUsername(username)
        .orElseThrow(() -> new IllegalStateException("User does not exist"));
    return jwtUtilService.generateToken(loginUser);
  }

  @Override
  public Optional<User> getUserbyUsername(String username) {
    return findUserByUsername(username);
  }

  private Optional<User> findUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }


}
