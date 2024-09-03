package com.fandresena.learn.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.service.JWTService;
// import com.fandresena.learn.service.RefreshTokenservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccessTokenController {

    // RefreshTokenservice refreshTokenService;
    JWTService jwtService;

    @GetMapping(path = "/access-token", produces = "application/json")
    public ResponseEntity<?> getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return ResponseEntity.status(401).body("No cookie found");
        }
        Map<String, String> refeshToken = jwtService.generateAccessTFromRefreshT(cookies);

        if (refeshToken != null && !refeshToken.isEmpty()) {
            return ResponseEntity.ok(refeshToken);
        } else
            return ResponseEntity.status(401).body("Refresh token not found");
    }
}
