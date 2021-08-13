package org.gfa.greenbay.repositories;

import java.util.Optional;
import org.gfa.greenbay.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findUserByUsername(String username);

}
