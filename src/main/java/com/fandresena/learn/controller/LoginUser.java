package com.fandresena.learn.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.LoginDTO;
import com.fandresena.learn.service.JWTService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginUser {

    private static final Logger logger = LoggerFactory.getLogger(LoginUser.class);
    
    @Qualifier("userAuthenticationManager")
    private  AuthenticationManager userAuthenticationManager;
    
    private  JWTService jwtService;

    @PostMapping(consumes = "application/json", path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try {
            // Authentification de l'utilisateur
            final Authentication authenticate = userAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

            // Si l'utilisateur est authentifié, générer les tokens
            if (authenticate.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(authenticate.getName());
                String refreshToken = jwtService.generateRefreshToken(authenticate.getName());
                
                // Créer un cookie pour le refreshToken
                ResponseCookie jwtCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .path("/")
                        .secure(false) // Mettez à true si vous utilisez HTTPS
                        .maxAge(48 * 60 * 60) // 48 heures
                        .sameSite("Lax") // Pour éviter les attaques CSRF
                        .build();

                // Ajouter le cookie à la réponse
                response.addCookie(new Cookie(jwtCookie.getName(), jwtCookie.getValue()));
                response.setHeader("Set-Cookie", jwtCookie.toString());

                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("accessToken", accessToken);
                return ResponseEntity.ok(responseBody);
            } else {
                // Si l'authentification échoue
                return ResponseEntity.status(401).body("Email or password is incorrect");
            }

        } catch (BadCredentialsException e) {
            // Gérer l'exception BadCredentialsException
            logger.warn("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(401).body("Email or password is incorrect");
        } catch (InternalAuthenticationServiceException e) {
            // Log l'erreur interne et retourner une réponse 404
            logger.warn("User doesn't exist: {}", e.getMessage());
            return ResponseEntity.status(404).body("User doesn't exist");
        } catch (Exception e) {
            logger.error("Error while authenticating user", e);
            return ResponseEntity.status(500).body("An error occurred while processing the request");
        }
    }

    // Gestion globale des erreurs internes (si nécessaire)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception e) {
        logger.error("Erreur serveur: ", e);
        return ResponseEntity.status(500).body("Internal server error");
    }
}
