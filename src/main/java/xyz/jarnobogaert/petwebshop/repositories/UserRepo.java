package xyz.jarnobogaert.petwebshop.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.jarnobogaert.petwebshop.models.User;

public interface UserRepo extends CrudRepository<User, Integer> {
}
