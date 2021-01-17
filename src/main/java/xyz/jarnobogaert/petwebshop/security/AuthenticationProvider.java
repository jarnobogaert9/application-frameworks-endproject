package xyz.jarnobogaert.petwebshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.jarnobogaert.petwebshop.services.MyUserService;

@Component
public class AuthenticationProvider {
    @Autowired
    MyUserService myUserService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider provider() {
        System.out.println("=============== CREATING PROVIDER ===============");
        // Create instance of provider and add user service +
        // password encoder so spring security knows how to work with the hashes
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // myUserService tells the provider how to retrieve a user
        provider.setUserDetailsService(myUserService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);

        return provider;
    }
}
