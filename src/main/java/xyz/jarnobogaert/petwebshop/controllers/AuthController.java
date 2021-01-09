package xyz.jarnobogaert.petwebshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.jarnobogaert.petwebshop.models.User;
import xyz.jarnobogaert.petwebshop.repositories.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public String registerUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        System.out.println("=============== REGISTER USER ===============");

        // If passwords do not match add error to binding result
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            System.out.println("Passwords do not match");
            FieldError fieldError = new FieldError("newUser", "confirmPassword", "Both passwords must match.");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            System.out.println("ERRORS in form of user creation");
            return "register";
        }

        // Check get user based on username and use it to check if he is already in the database
        Optional<User> found = userRepo.findByUsername(user.getUsername());

        if (found.isPresent()) {
            System.out.println("Found user with username: " + found.get().getUsername());
            FieldError fieldError = new FieldError("newUser", "username", "Username is already in use.");
            bindingResult.addError(fieldError);
            return "register";
        }

        System.out.println("No user found");
        // Create user in database
        // First create hash for his password
        String hash = bCryptPasswordEncoder.encode(user.getPassword());
        user.setHash(hash);
        userRepo.save(user);

        // Log user in after saving him/her to the database
        try {
            // Pass plain text password because our authentication provider will use this in the password encoder
            request.login(user.getUsername(), user.getPassword());
        } catch (ServletException exception) {
            throw new RuntimeException("Servlet exception: something went wrong when trying to login a user\n" + exception.getStackTrace() + "\n" + exception.getMessage() + "\n" + exception.getRootCause());
        }

        return "redirect:/products";
    }
}
