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
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart")
    public String cart(@RequestParam(value = "productId", required = false) String productId, Principal principal, ModelMap modelMap) {
        // Get user based on logged in user his/her username
        User user = userRepo.findByUsername(principal.getName()).get();

        if (productId == null) {
            System.out.println("============ GET: /cart ============");
            List<Product> products = user.getProducts();

            // Set model attribute so we can access it in our template
            modelMap.addAttribute("products", products);

            return "cart";
        }
        System.out.println("============ Add to card ============");
        System.out.println(productId);

        // Get the product that we need to add to the user his/her cart
        Optional<Product> product = productRepo.findById(Integer.parseInt(productId));

        // Add product id to the list of products, make sure there is a product so avoid exceptions
        if (product.isPresent())
            user.getProducts().add(product.get());

        // Save the changes
        userRepo.save(user);

        return "redirect:/cart";
    }

    @PostMapping("/cart")
    public String removeProduct(@RequestParam(value = "productId") String productId, Principal principal, ModelMap modelMap) {
        // /cart routes are only accessible if they are authenticated so we would always get a user back
        User user = userRepo.findByUsername(principal.getName()).get();

        Optional<Product> product = productRepo.findById(Integer.parseInt(productId));

        if (product.isPresent())
            user.getProducts().remove(product.get());

        userRepo.save(user);

        return "redirect:/cart";
    }
}
