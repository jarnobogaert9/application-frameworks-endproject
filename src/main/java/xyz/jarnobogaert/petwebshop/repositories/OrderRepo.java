package xyz.jarnobogaert.petwebshop.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.jarnobogaert.petwebshop.models.Order;

public interface OrderRepo extends CrudRepository<Order, Integer> {
}
