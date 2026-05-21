package com.luxuryproductsholding.api.services;

import com.luxuryproductsholding.api.dao.CustomUserRepository;
import com.luxuryproductsholding.api.models.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final CustomUserRepository userRepository;

    public UserService(CustomUserRepository userDAO) {
        this.userRepository = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String roleName = customUser.getRole().getEmail();

        return new User(
                customUser.getEmail(),
                customUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleName)) // -> "ROLE_ADMIN"
        );
    }


    public CustomUser updateUserEmail(String currentEmail, String newEmail) {
        CustomUser user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setEmail(newEmail);
        return userRepository.save(user);
    }
}

