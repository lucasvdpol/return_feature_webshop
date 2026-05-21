package com.luxuryproductsholding.api.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.luxuryproductsholding.api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JWTUtil jwtTokenUtil;
    private String email;

    public JWTFilter(UserService userService, JWTUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String getEmail() {
        return email;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                String emailFromToken = jwtTokenUtil.validateTokenAndRetrieveSubject(jwt);
                this.email = emailFromToken;
                UserDetails userDetails = userService.loadUserByUsername(emailFromToken);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (JWTVerificationException exc) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            } catch (UsernameNotFoundException exc) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login again");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}