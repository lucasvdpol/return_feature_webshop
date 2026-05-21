package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.config.JWTUtil;
import com.luxuryproductsholding.api.dao.CustomUserRepository;
import com.luxuryproductsholding.api.dao.RoleRepository;
import com.luxuryproductsholding.api.dto.AuthenticationDTO;
import com.luxuryproductsholding.api.dto.LoginResponse;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.models.Role;
import com.luxuryproductsholding.api.services.CredentialValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CredentialValidator validator;

    public AuthController(CustomUserRepository userRepository, JWTUtil jwtUtil, AuthenticationManager authManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, CredentialValidator validator) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.validator = validator;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody AuthenticationDTO authenticationDTO) {
        if (!validator.isValidEmail(authenticationDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid email provided");
        }

        if (!validator.isValidPassword(authenticationDTO.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid password provided");
        }

        if (userRepository.findByEmail(authenticationDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(authenticationDTO.getPassword());

        Role role = roleRepository.findByEmail("ROLE_USER");
        CustomUser newUser = new CustomUser(authenticationDTO.getEmail(), encodedPassword, role);

        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail());

        return ResponseEntity.ok(new LoginResponse(newUser.getEmail(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword());
            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(authenticationDTO.getEmail());

            Optional<CustomUser> user = userRepository.findByEmail(authenticationDTO.getEmail());

            if (user.isPresent()) {
                return ResponseEntity.ok(new LoginResponse(user.get().getEmail(), token));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
        }
    }
}
