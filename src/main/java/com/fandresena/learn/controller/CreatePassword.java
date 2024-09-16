package com.fandresena.learn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.NewPasswordTokenService;
import com.fandresena.learn.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/createPassword")
public class CreatePassword {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private NewPasswordTokenService newPasswordTokenService;
    private UserService userService;

    public record PasswordDTO(String password) {

    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createPassword(@RequestParam String tkn, @RequestBody PasswordDTO data) {
        boolean valid = false;
        String password = data.password();
        
        try {
            valid = newPasswordTokenService.isvallidToken(tkn);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("token invalid");
        }
       
        if (valid) {
            UserModel user = newPasswordTokenService.findUser(tkn);
            userService.changePassword(user, password);
            newPasswordTokenService.deleteExpiredTokens();
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("token expired");
        }

    }
}
