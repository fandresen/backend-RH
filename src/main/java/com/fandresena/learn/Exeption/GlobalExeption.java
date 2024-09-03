package com.fandresena.learn.Exeption;

import java.security.SignatureException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class GlobalExeption extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExpiredJwtException.class)
     public ResponseEntity<String> handleExpiredJwtExeption(ExpiredJwtException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("token expired");
    }

    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, UnsupportedJwtException.class})
    public ResponseEntity<String> handleInvalidJwtExeption(Exception ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("invalid token");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidTokenExeption(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("token is missing or empty");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExeption(Exception ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("Internal server error");
    }
}
