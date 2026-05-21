package com.luxuryproductsholding.api.services;

import com.luxuryproductsholding.api.config.JWTUtil;
import com.luxuryproductsholding.api.dao.CustomUserRepository;
import com.luxuryproductsholding.api.dao.OrderEntityRepository;
import com.luxuryproductsholding.api.dto.LoginResponse;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.models.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserService {
    private static final Logger log = LoggerFactory.getLogger(CustomUserService.class);
    private final CustomUserRepository userRepository;
    private final OrderEntityRepository orderEntityRepository;
    private final JWTUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public CustomUserService(CustomUserRepository userRepository,
                             OrderEntityRepository orderEntityRepository,
                             JWTUtil jwtTokenUtil,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderEntityRepository = orderEntityRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public CustomUser addUser(CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public LoginResponse updateUserEmail(String currentEmail, String newEmail) {
        CustomUser existingUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + currentEmail));

        existingUser.setEmail(newEmail);
        updateAssociatedOrders(currentEmail, newEmail);

        CustomUser savedUser = userRepository.save(existingUser);
        String newToken = jwtTokenUtil.generateToken(savedUser.getEmail());
        return new LoginResponse(savedUser.getEmail(), newToken);
    }

    @Transactional
    protected void updateAssociatedOrders(String oldEmail, String newEmail) {
        List<OrderEntity> orders = orderEntityRepository.findByCustomerName(oldEmail);
        orders.forEach(order -> {
            order.setCustomerName(newEmail);
            orderEntityRepository.save(order);
        });
    }
}