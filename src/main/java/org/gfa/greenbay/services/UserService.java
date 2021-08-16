package org.gfa.greenbay.services;

import java.util.Optional;
import org.gfa.greenbay.models.User;

public interface UserService {

  String generateToken(String username);

  Optional<User> getUserbyUsername(String username);
}
