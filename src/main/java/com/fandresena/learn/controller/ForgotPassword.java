package com.fandresena.learn.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.NewPasswordTokenService;
import com.fandresena.learn.service.SendEmailService;
import com.fandresena.learn.service.TokenGeneratorService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/forgotPassword")
public class ForgotPassword {
    
    UserDAO userDAO;
    private NewPasswordTokenService newPasswordTokenService;

    private record Email(String email) {

    }
    
    @PostMapping
    public ResponseEntity<?> emailExist(@RequestBody Email email)throws IOException {
        UserModel user = userDAO.findByEmail(email.email());
        if(user != null){

            //delete old token
            newPasswordTokenService.deleteTokenByUserId(user.getId());

            String token = TokenGeneratorService.generatepassword(12);
            String realToken = newPasswordTokenService.createToken(user, token);
            String template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/ForgetPassword.html")));
            SendEmailService.sendEmail(user.getEmail(), "Compte ZenRH", user.getFirst_name() , " http://197.158.88.203:1407/newPassword?tkn="+realToken,template);

            return ResponseEntity.ok("email sent successfully");
        }
        else{
            return ResponseEntity.badRequest().body("cannot find user with that email");
        }
    }
}
