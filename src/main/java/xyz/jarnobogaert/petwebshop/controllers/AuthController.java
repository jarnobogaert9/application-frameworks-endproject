package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.jarnobogaert.petwebshop.models.User;

import javax.validation.Valid;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("newUser")
    public User toSave() {
        return new User();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    // Valid annotation to validate the incoming object
    public String registerUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        System.out.println("---- REGISTER USER ----");
        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS in form of user creation");
            return "register";
        }
        return "register";
    }
}
