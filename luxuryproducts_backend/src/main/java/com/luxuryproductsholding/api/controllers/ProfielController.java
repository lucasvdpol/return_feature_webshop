package com.luxuryproductsholding.api.controllers;


import com.luxuryproductsholding.api.config.JWTUtil;
import com.luxuryproductsholding.api.dao.CustomUserRepository;
import com.luxuryproductsholding.api.dao.ProfielDAO;
import com.luxuryproductsholding.api.dto.ProfielDTO;
import com.luxuryproductsholding.api.models.CustomUser;
import com.luxuryproductsholding.api.models.Profiel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profiel")
public class ProfielController {

    private final ProfielDAO profielDAO;
    private final JWTUtil jwtUtil;
    private final CustomUserRepository customUserRepository;


    public ProfielController(ProfielDAO profielDAO, JWTUtil jwtUtil, CustomUserRepository customUserRepository) {
        this.profielDAO = profielDAO;
        this.jwtUtil = jwtUtil;
        this.customUserRepository = customUserRepository;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<Profiel> getOwnProfiel(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.validateTokenAndRetrieveSubject(token);
        Optional<CustomUser> userOptional = customUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Long userId = userOptional.get().getId();

        Optional<Profiel> profielOptional = profielDAO.findProfielByUserId(userId);
        return profielOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Profiel>> getProfiel() {

        return ResponseEntity.ok(this.profielDAO.getProfiel());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<String> addProfiel(@RequestBody ProfielDTO profielDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.validateTokenAndRetrieveSubject(token);
        Optional<CustomUser> userOptional = customUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        profielDTO.userId = userOptional.get().getId();

        this.profielDAO.makeProfiel(profielDTO);
        return ResponseEntity.ok("Profiel gemaakt");
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping
    public ResponseEntity<String> updateOwnProfiel(@RequestBody ProfielDTO profielDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.validateTokenAndRetrieveSubject(token);
        Optional<CustomUser> userOptional = customUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Long userId = userOptional.get().getId();
        profielDTO.userId = userId;

        profielDAO.updatingProfiel(userId, profielDTO);

        return ResponseEntity.ok("Profiel bijgewerkt voor gebruiker met ID: " + userId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfiel(@PathVariable long id) {
        this.profielDAO.deleteProfiel(id);
        return ResponseEntity.ok("Profiel verwijderd met id" + id);
    }

}
