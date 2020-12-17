package xyz.jarnobogaert.petwebshop.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.jarnobogaert.petwebshop.models.Category;
import xyz.jarnobogaert.petwebshop.models.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {
    Iterable<Product> findByCategory(Category c);
}
