package com.fandresena.learn.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.LoginDTO;
// import com.fandresena.learn.service.AccessTokenService;
import com.fandresena.learn.service.JWTService;
// import com.fandresena.learn.service.RefreshTokenservice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LoginUser {
    private AuthenticationManager authenticationManager;
    // private AccessTokenService accessTokenService;
    // private RefreshTokenservice refreshTokenservice;
    private JWTService jwtService;

    @PostMapping(consumes = "application/json", path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO,HttpServletResponse res ) {

        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

        if (authenticate.isAuthenticated()) {
            String accessToken = jwtService.generateAccessToken(authenticate.getName());
            String refreshToken = jwtService.generateRefreshToken(authenticate.getName());
            Map<String, String> response = new HashMap<String,String>();
            response.put("accessToken", accessToken);
            ResponseCookie jwtCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .secure(false)
                    .maxAge(48 * 60 * 60) 
                    .sameSite("None") // Pour éviter les attaques CSRF
                    .build();

            // Ajoutez le cookie à la réponse HTTP
            res.addHeader("Set-Cookie", jwtCookie.toString());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Authentication failed");
        }
    }

}