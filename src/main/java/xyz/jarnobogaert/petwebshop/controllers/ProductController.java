package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.jarnobogaert.petwebshop.models.Category;
import xyz.jarnobogaert.petwebshop.models.Product;
import xyz.jarnobogaert.petwebshop.repositories.ProductRepo;

import java.util.Arrays;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(@RequestParam(value = "c", required = false) String c, Model model) {
        System.out.println("===================== " + c + " =====================");
        Iterable<Product> products = null;
        if (c != null) {
            Category category = null;
            try {
                category = Category.valueOf(c.toUpperCase());
                products = productRepo.findByCategory(category);
            } catch (Exception ex) {
                System.out.println("Could not parse category");
                // Empty array because no valid category was passed
                products = Arrays.asList();
            }
        } else {
            products = productRepo.findAll();
        }

        // Pass products to template by attribute => in our template we can access this variable
        model.addAttribute("products", products);
        return "products";
    }
}
