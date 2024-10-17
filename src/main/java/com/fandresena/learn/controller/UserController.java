package com.fandresena.learn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.UserDTO;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.NewPasswordTokenService;
import com.fandresena.learn.service.SendEmailService;
import com.fandresena.learn.service.TokenGeneratorService;
import com.fandresena.learn.service.UserService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    UserService userService;
    JWTService jwtService;
    NewPasswordTokenService newPasswordTokenService;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserModel user, HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);

        try{
            userService.createEntireuser(user,token);
            return ResponseEntity.ok("User created successfully");
        }
        catch(Exception e){
            logger.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body("Error creationg user");
        }


    }

    @GetMapping(path = "/department", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CHEF_DEP')  or hasAuthority('USER')")
    public ResponseEntity<?> getAllUserByDepartement(HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int departmentId = jwtService.extractDepartementId(token);
        try {
            List<UserModel> users = userService.getByDepartementId(departmentId);
            List<UserDTO> userDTOs = new ArrayList<>();
            for (UserModel userModel : users) {
                userDTOs.add(userService.convertToDTO(userModel));
            }
            return ResponseEntity.ok(userDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving users");
        }

    }

    @GetMapping(path = "/entreprise", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEP_CHEF')")
    public ResponseEntity<?> getAllUserByEntrepriseId(HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        try {
            List<UserModel> users = userService.getAllByEntrepriseId(entrepriseId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body("Error retrieving users ");
        }

    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getUserById(@RequestParam(name = "id") int id){
        try {
            UserModel user = userService.getUserById(id);
            UserDTO userDTO = userService.convertToDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving user");
        }
    }

}
