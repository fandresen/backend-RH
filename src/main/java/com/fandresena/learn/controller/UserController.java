package com.fandresena.learn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.UserService;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    UserService userService;
    JWTService jwtService;
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEP_CHEF')")
    public ResponseEntity<?> createUser (@Valid @RequestBody UserModel user,HttpServletRequest res){
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        user.setEntreprise_id(entrepriseId);
        try{
            userService.createUser(user);
            return ResponseEntity.ok("User created successfully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error creating user");
        }
    }

    @GetMapping(path = "/department",produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEP_CHEF')  or hasAuthority('USER')")
    public ResponseEntity<?> getAllUserByDepartement(@RequestParam("departement") int id){
        try{
            List<UserModel> users = userService.getByDepartementId(id);
            return ResponseEntity.ok(users);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error retrieving users");
        }
     
    }

    @GetMapping(path = "/entreprise",produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEP_CHEF')  or hasAuthority('USER')")
    public ResponseEntity<?> getAllUserByEntrepriseId( HttpServletRequest res){
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        try{
            List<UserModel> users = userService.getAllByEntrepriseId(entrepriseId);
            return ResponseEntity.ok(users);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error retrieving users");
        }
     
    }



    
}
