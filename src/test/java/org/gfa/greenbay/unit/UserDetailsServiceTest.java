package org.gfa.greenbay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.UserRepository;
import org.gfa.greenbay.security.UserDetailsImpl;
import org.gfa.greenbay.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserDetailsServiceImpl userDetailsService;

  @Test
  public void loadUserByUsernameTest_UsernameNotFoundExceptionThrown_repositoryReturnEmpty() {
    String testUsername = "username";

    when(userRepository.findUserByUsername(testUsername)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class,
        () -> userDetailsService.loadUserByUsername(testUsername));
  }

  @Test
  public void loadUserByUsernameTest_returnUserDetails_repositoryReturnTestUser() {
    String testUsername = "username";
    User testUser = new User(1L, testUsername, "password", "ROLE_USER");
    UserDetails testUserDetails = new UserDetailsImpl(testUser);

    when(userRepository.findUserByUsername(testUsername)).thenReturn(Optional.of(testUser));

    assertEquals(testUserDetails, userDetailsService.loadUserByUsername(testUsername));
  }

}
