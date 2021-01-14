package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
    public String products(@RequestParam(value = "c", required = false) String c, ModelMap modelMap) {
        System.out.println("===================== " + c + " =====================");
        Iterable<Product> products = null;

        // c is the category, so if there is a category provided we filter the products based on that category
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
        }
        // If there is no category specified we return all the products available
        else {
            products = productRepo.findAll();
        }

        // Pass products to template by attribute => in our template we can access this variable
        modelMap.addAttribute("products", products);
        return "products";
    }
}
