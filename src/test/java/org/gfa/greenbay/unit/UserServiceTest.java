package org.gfa.greenbay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.UserRepository;
import org.gfa.greenbay.security.JwtUtilService;
import org.gfa.greenbay.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  UserServiceImpl userService;
  @Mock
  private JwtUtilService jwtUtilService;
  @Mock
  private UserRepository userRepository;

  @Test
  public void generateTokenTest_returnToken_rightValue() {
    String testUsername = "username";
    User testUser = new User();
    Optional<User> testUserOptional = Optional.of(new User());
    String testToken = "token";

    when(userRepository.findUserByUsername(testUsername)).thenReturn(testUserOptional);
    when(jwtUtilService.generateToken(testUser)).thenReturn(testToken);

    assertEquals("token", userService.generateToken("username"));
  }

}
