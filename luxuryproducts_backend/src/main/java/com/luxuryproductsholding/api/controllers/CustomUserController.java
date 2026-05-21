package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.config.JWTUtil;
import com.luxuryproductsholding.api.dto.LoginResponse;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.services.CustomUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CustomUserController {
    private final CustomUserService customUserService;
    private final JWTUtil jwtTokenUtil;


    public CustomUserController(CustomUserService customUserService, JWTUtil jwtTokenUtil) {
        this.customUserService = customUserService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CustomUser> getAllUsers() {
        return customUserService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public CustomUser addUser(@RequestBody CustomUser user) {
        return this.customUserService.addUser(user);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update/{email}")
    public ResponseEntity<LoginResponse> updateUserEmail(
            @PathVariable String email,
            @RequestBody String newEmail,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String tokenEmail = jwtTokenUtil.validateTokenAndRetrieveSubject(
                    authHeader.replace("Bearer ", ""));

            if (!tokenEmail.equals(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            LoginResponse response = customUserService.updateUserEmail(email, newEmail);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
