package xyz.jarnobogaert.petwebshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xyz.jarnobogaert.petwebshop.models.Category;
import xyz.jarnobogaert.petwebshop.models.Product;
import xyz.jarnobogaert.petwebshop.repositories.ProductRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PetwebshopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PetwebshopApplication.class, args);

        ProductRepo productRepo = context.getBean(ProductRepo.class);
        Iterable<Product> products = productRepo.findAll();
        List<Product> productList = new ArrayList<>();
        products.forEach(productList::add);

        if (productList.isEmpty()) {
            productRepo.saveAll(Arrays.asList(
                    new Product("Fresh chicken pieces", "Bag full of fresh chicken pieces.", Category.NUTRITION, 29.99F),
                    new Product("Bite stick", "Stick made out of hard material. Long lifetime", Category.TOYS, 9.99F),
                    new Product("Ball", "Ball made out of rubber.", Category.TOYS, 12.95F),
                    new Product("Guinea pig cage", "Guinea pig cage made out of durable metal. 60L X 60W X 67H.", Category.HABITAT, 12.95F),
                    new Product("Cup lamb & beef (85g)", "Cup of lamb & beef for adult cats. Grain-free, lots of fresh meat & all-natural ingredients.", Category.NUTRITION, 0.99F)
            ));
        }
    }

}
