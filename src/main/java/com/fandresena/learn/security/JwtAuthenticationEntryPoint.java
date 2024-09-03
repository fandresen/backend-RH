package com.fandresena.learn.security;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // // Envoyer un code d'état 401 si le token est expiré ou non valide
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is expired or invalid");
    }
}
