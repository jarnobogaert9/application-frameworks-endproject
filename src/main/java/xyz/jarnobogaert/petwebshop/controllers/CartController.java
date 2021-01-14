package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.jarnobogaert.petwebshop.models.Product;
import xyz.jarnobogaert.petwebshop.models.User;
import xyz.jarnobogaert.petwebshop.repositories.ProductRepo;
import xyz.jarnobogaert.petwebshop.repositories.UserRepo;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class CartController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/cart")
    public String cart(@RequestParam(value = "productId", required = false) String productId, Principal principal, ModelMap modelMap) {
        // Get user based on logged in user his/her username
        User user = userRepo.findByUsername(principal.getName()).get();

        // If there is not productId the user just wants to see the cart items
        if (productId == null) {
            System.out.println("============ GET: /cart ============");
            List<Product> products = user.getProducts();

            // Set model attribute so we can access it in our template and list the cart items
            modelMap.addAttribute("products", products);

            return "cart";
        }

        // If there is a productId, the user wants to add this product to his cart
        System.out.println("============ Add to cart ============");
        System.out.println(productId);

        // Get the product that we need to add it to the user his/her cart
        Optional<Product> product = productRepo.findById(Integer.parseInt(productId));

        // Add product to the list of products, make sure there is a product to avoid exceptions
        if (product.isPresent())
            user.getProducts().add(product.get());

        // Save the changes
        userRepo.save(user);

        return "redirect:/cart";
    }

    @PostMapping("/cart")
    public String removeProduct(@RequestParam(value = "productId") String productId, Principal principal, ModelMap modelMap) {
        User user = userRepo.findByUsername(principal.getName()).get();

        Optional<Product> product = productRepo.findById(Integer.parseInt(productId));

        // Only remove product if the product was found, otherwise it will throw a null pointer exception
        if (product.isPresent())
            user.getProducts().remove(product.get());

        userRepo.save(user);

        return "redirect:/cart";
    }
}
