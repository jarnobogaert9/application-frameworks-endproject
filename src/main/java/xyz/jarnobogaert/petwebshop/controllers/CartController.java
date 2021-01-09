package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import xyz.jarnobogaert.petwebshop.models.Product;
import xyz.jarnobogaert.petwebshop.models.User;
import xyz.jarnobogaert.petwebshop.repositories.ProductRepo;
import xyz.jarnobogaert.petwebshop.repositories.UserRepo;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart")
    public String cart(@RequestParam(value = "productId", required = false) String productId, Principal principal) {
        if (productId == null) {
            System.out.println("============ GET: /cart ============");
            return "cart";
        }
        System.out.println("============ Add to card ============");
        System.out.println(productId);

        // TODO add product id to a list of a user so we can track products in the cart
        // Get user based on logged in user his/her username
        User user = userRepo.findByUsername(principal.getName()).get();

        // Get the product that we need to add to the user his/her cart
        Product product = productRepo.findById(Integer.parseInt(productId)).get();

        user.getProducts().add(product);

        // Save the changes
        userRepo.save(user);

        return "redirect:/cart";
    }
}
