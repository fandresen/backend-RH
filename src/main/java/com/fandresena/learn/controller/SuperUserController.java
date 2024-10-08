package com.fandresena.learn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.LoginDTO;

import com.fandresena.learn.model.SuperUserModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.SuperUserService;
import com.fandresena.learn.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/superuser")
public class SuperUserController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private AuthenticationManager superUserAuthenticationManager;
    private final SuperUserService superUserService;
    private JWTService jwtService;

    @Autowired
    public SuperUserController(SuperUserService superUserService,
            @Qualifier("superUserAuthenticationManager") AuthenticationManager superUserAuthenticationManager,
            JWTService jwtService) {
        this.superUserService = superUserService;
        this.superUserAuthenticationManager = superUserAuthenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SuperUserModel>> getAllSuperUsers() {
        List<SuperUserModel> superUsers = superUserService.getAllSuperUsers();
        return ResponseEntity.ok(superUsers);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createSuperUser(@Valid @RequestBody SuperUserModel superUserModel) {
        try {
            superUserService.createSuperUser(superUserModel);
            return ResponseEntity.ok("Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Login for Super User
    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> loginSuperUser(@RequestBody LoginDTO loginDTO, HttpServletResponse res) {
        try {
            final Authentication authenticate = superUserAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

            if (authenticate.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(authenticate.getName());
                String refreshToken = jwtService.generateRefreshToken(authenticate.getName());
                Map<String, String> response = new HashMap<String, String>();
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
                // Si l'authentification échoue
                return ResponseEntity.status(401).body("Email or password is incorrect");
            }
        } catch (BadCredentialsException e) {
            //Mot de passe incorrect
            return ResponseEntity.status(401).body("Password is incorrect");
        } catch (InternalAuthenticationServiceException e) {
            //utilisateru n'existe pas
            return ResponseEntity.status(401).body("User doesn't exist");
        }

        catch (Exception e) {
            logger.error("Error while authenticating user", e);
            return ResponseEntity.status(500).body("An error occurred while processing the request");
        }

    }
}
