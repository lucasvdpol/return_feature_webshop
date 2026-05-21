package com.luxuryproductsholding.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
    }

    public String generateToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.trim());
            return JWT.create()
                    .withSubject("User Details")
                    .withClaim("email", email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(createExpirationDate())
                    .withIssuer("Duck Studios")
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("JWT Token creation failed", e);
        }
    }

    public String validateTokenAndRetrieveSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.trim());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject("User Details")
                    .withIssuer("Duck Studios")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("email").asString();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("JWT Verification Failed: " + e.getMessage());
        }
    }

    private Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + 3600000);
    }
}
