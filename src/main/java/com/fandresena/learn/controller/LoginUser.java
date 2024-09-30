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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginUser {

    private static final Logger logger = LoggerFactory.getLogger(LoginUser.class);
    @Qualifier("userAuthenticationManager")
    private AuthenticationManager userAuthenticationManager;
    private JWTService jwtService;

    @PostMapping(consumes = "application/json", path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Authentification de l'utilisateur
            final Authentication authenticate = userAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

            // Si l'utilisateur est authentifié, générer les tokens
            if (authenticate.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(authenticate.getName());
                String refreshToken = jwtService.generateRefreshToken(authenticate.getName());
                Map<String, String> response = new HashMap<>();
                response.put("accessToken", accessToken);

                // Créer un cookie pour le refreshToken
                ResponseCookie jwtCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .path("/")
                        .secure(false)
                        .maxAge(48 * 60 * 60)
                        .sameSite("None") // Pour éviter les attaques CSRF
                        .build();

                // Retourner la réponse avec le refreshToken
                return ResponseEntity.ok().header("Set-Cookie", jwtCookie.toString()).body(response);
            } else {
                // Si l'authentification échoue
                return ResponseEntity.status(401).body("Email or password is incorrect");
            }

        } catch (BadCredentialsException e) {
            // Gérer l'exception BadCredentialsException
            return ResponseEntity.status(401).body("Password is incorrect");
        } catch (InternalAuthenticationServiceException e) {
            // Log l'erreur interne et retourner une réponse 500
            return ResponseEntity.status(401).body("User doesn't exist");
        }
        catch(Exception e){
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
