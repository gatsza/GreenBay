package org.gfa.greenbay.services;

import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.dtos.LoginResponseDTO;
import org.gfa.greenbay.exceptions.LoginInformationMissingException;
import org.gfa.greenbay.exceptions.LoginInformationWrongException;
import org.gfa.greenbay.exceptions.PasswordMissingException;
import org.gfa.greenbay.exceptions.UsernameMissingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private final AuthenticationManager authenticationManager;
  private UserService userService;

  public LoginServiceImpl(UserService userService,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public LoginResponseDTO login(LoginRequestDTO loginRequest) {
    validateLoginRequest(loginRequest);
    authenticateUser(loginRequest);
    String userToken = userService.generateToken(loginRequest.getUsername());
    return new LoginResponseDTO(userToken);
  }

  private void authenticateUser(LoginRequestDTO request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(),
              request.getPassword()
          )
      );
    } catch (BadCredentialsException e) {
      throw new LoginInformationWrongException();
    }
  }

  private void validateLoginRequest(LoginRequestDTO loginRequest) {
    if (loginRequest == null) {
      throw new LoginInformationMissingException();
    }

    boolean notHasUsername = isUsernameMissing(loginRequest);
    boolean notHasPassword = isPasswordMissing(loginRequest);

    if (notHasUsername && notHasPassword) {
      throw new LoginInformationMissingException();
    }

    if (notHasUsername) {
      throw new UsernameMissingException();
    }

    if (notHasPassword) {
      throw new PasswordMissingException();
    }
  }

  private boolean isUsernameMissing(LoginRequestDTO loginRequest) {
    return loginRequest.getUsername() == null;
  }

  private boolean isPasswordMissing(LoginRequestDTO loginRequest) {
    return loginRequest.getPassword() == null;
  }

}
