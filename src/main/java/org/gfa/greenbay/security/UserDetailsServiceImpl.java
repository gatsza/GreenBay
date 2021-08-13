package org.gfa.greenbay.security;


import java.util.Optional;
import org.gfa.greenbay.models.User;
import org.gfa.greenbay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByUsername(username);
    user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username + "!"));
    return user.map(UserDetailsImpl::new).get();
  }
}

