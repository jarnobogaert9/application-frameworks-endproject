package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.jarnobogaert.petwebshop.models.Order;
import xyz.jarnobogaert.petwebshop.models.Product;
import xyz.jarnobogaert.petwebshop.models.User;
import xyz.jarnobogaert.petwebshop.repositories.OrderRepo;
import xyz.jarnobogaert.petwebshop.repositories.ProductRepo;
import xyz.jarnobogaert.petwebshop.repositories.UserRepo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;

    @PostMapping("/order")
    public String placeOrder(Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).get();
        List<Product> products = new ArrayList<>();
        float totalPrice = 0F;

        // Make new array instead of using array from user object, otherwise it will give a conflict about shared collections
        user.getProducts().forEach(products::add);

        // Get price of each product and accumulate it to get the total price of the order
        for (Product product : user.getProducts()) {
            totalPrice += product.getPrice();
        }

        // Create new order
        Order order = new Order();
        order.setOwner(user);
        order.setProducts(products);
        order.setTotalPrice(totalPrice);

        orderRepo.save(order);

        // Clean the cart of the user after creating the order
        user.getProducts().clear();
        userRepo.save(user);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String getOrders(Principal principal, ModelMap modelMap) {
        User user = userRepo.findByUsername(principal.getName()).get();
        List<Order> orders = user.getOrders();

        // Add products as model attribute to access it in our template
        modelMap.addAttribute("orders", orders);

        return "orders";
    }
}
