package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDAO {
    private final CustomUserRepository customUserRepository;

    public CustomUserDAO(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    public void addUser( CustomUser customUser ) {
        this.customUserRepository.save(customUser);
    }

}
