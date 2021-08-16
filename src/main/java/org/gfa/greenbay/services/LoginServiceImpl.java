package org.gfa.greenbay.services;

import java.util.ArrayList;
import java.util.List;
import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.dtos.LoginResponseDTO;
import org.gfa.greenbay.exceptions.LoginInformationWrongException;
import org.gfa.greenbay.exceptions.LoginPayloadMissingException;
import org.gfa.greenbay.exceptions.MultipleLoginFieldMissingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;

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
      throw new LoginPayloadMissingException();
    }

    List<String> missingFields = new ArrayList<>();

    if (loginRequest.getUsername() == null) {
      missingFields.add("username");
    }

    if (loginRequest.getPassword() == null) {
      missingFields.add("password");
    }

    if (missingFields.size() != 0) {
      throw new MultipleLoginFieldMissingException(missingFields);
    }
  }

}
