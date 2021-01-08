package xyz.jarnobogaert.petwebshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.jarnobogaert.petwebshop.models.User;
import xyz.jarnobogaert.petwebshop.repositories.UserRepo;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(s);
        if (user.isPresent()) {
            // User is found -> return user
            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getHash(),
                    Arrays.asList(new SimpleGrantedAuthority("USER")));
        }
        throw new UsernameNotFoundException("");
    }
}

