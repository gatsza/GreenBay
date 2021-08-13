package org.gfa.greenbay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.exceptions.LoginInformationMissingException;
import org.gfa.greenbay.exceptions.LoginInformationWrongException;
import org.gfa.greenbay.exceptions.PasswordMissingException;
import org.gfa.greenbay.exceptions.UsernameMissingException;
import org.gfa.greenbay.services.LoginServiceImpl;
import org.gfa.greenbay.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  UserService userService;

  @Mock
  AuthenticationManager authenticationManager;

  @InjectMocks
  LoginServiceImpl loginService;

  @Test
  public void loginLoginTest_LoginInformationMissingExceptionThrown_requestNull(){
    LoginRequestDTO testRequest = null;

    assertThrows(LoginInformationMissingException.class, () -> loginService.login(testRequest));
  }

  @Test
  public void loginTest_LoginInformationMissingExceptionThrown_usernameNull_passwordNull(){
    LoginRequestDTO testRequest = new LoginRequestDTO(null,null);

    assertThrows(LoginInformationMissingException.class, () -> loginService.login(testRequest));
  }

  @Test
  public void loginTest_PasswordMissingExceptionThrown_usernameNotNull_passwordNull(){
    LoginRequestDTO testRequest = new LoginRequestDTO("username",null);

    assertThrows(PasswordMissingException.class, () -> loginService.login(testRequest));
  }

  @Test
  public void loginTest_usernameMissingExceptionThrown_usernameNull_passwordNotNull(){
    LoginRequestDTO testRequest = new LoginRequestDTO(null,"password");

    assertThrows(UsernameMissingException.class, () -> loginService.login(testRequest));
  }

  @Test
  public void loginTest_usernameMissingExceptionThrown_usernameNotNull_passwordNotNull(){
    LoginRequestDTO testRequest = new LoginRequestDTO("username","password");

    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username","password")))
        .thenThrow(new BadCredentialsException("test exception"));

    assertThrows(LoginInformationWrongException.class, () -> loginService.login(testRequest));
  }

  @Test
  public void loginTest_tokenReturn_usernameNotNull_passwordNotNull(){
    LoginRequestDTO testRequest = new LoginRequestDTO("username","password");

    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username","password")))
        .thenReturn(null);
    String testToken = "token";
    when(userService.generateToken("username")).thenReturn(testToken);

    assertEquals(testToken,  loginService.login(testRequest).getToken());
  }

}
