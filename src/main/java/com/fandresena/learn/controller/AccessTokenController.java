package com.fandresena.learn.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AccessTokenController.class);

    @GetMapping(path = "/access-token", produces = "application/json")
    public ResponseEntity<?> getAccessToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            StringBuilder cookieString = new StringBuilder();
            for (Cookie cookie : cookies) {
                cookieString.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
            }
            logger.info("Cookies: {}", cookieString.toString());
        }


        if (cookies == null || cookies.length == 0) {
            return ResponseEntity.status(403).body("No cookie found");
        }
        Map<String, String> refeshToken = jwtService.generateAccessTFromRefreshT(cookies);

        if (refeshToken != null && !refeshToken.isEmpty()) {
            return ResponseEntity.ok(refeshToken);
        } else
            return ResponseEntity.status(401).body("Refresh token not found");
    }
}
