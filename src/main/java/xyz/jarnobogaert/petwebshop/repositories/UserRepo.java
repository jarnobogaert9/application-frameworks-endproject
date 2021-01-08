package xyz.jarnobogaert.petwebshop.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.jarnobogaert.petwebshop.models.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
