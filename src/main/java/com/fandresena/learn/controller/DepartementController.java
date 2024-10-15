package com.fandresena.learn.controller;


import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.DepartementDTO;
import com.fandresena.learn.dao.DepartementDAO;
import com.fandresena.learn.model.DepartementModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/departement")
public class DepartementController {
    DepartementDAO departementDAO;
    UserService userService;
    JWTService jwtService;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartementDTO departementDTO,HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        DepartementModel departementModel = departementDTO.convertToModel();
        departementModel.setEntreprise_id(entrepriseId);
        try {
            userService.createEntireuser(departementDTO.user(), token);
            departementDAO.createDepartement(departementModel, departementModel.getEntreprise_id());
            return ResponseEntity.ok("Department created successfully");
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.badRequest().body("Error creating department");
        }
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllDepartementByEntreprise(HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        try {
            List<DepartementModel> departements = departementDAO.getAllDepartementByEntreprise(entrepriseId);
            return ResponseEntity.ok(departements);

        } catch (Exception e) {
            // e.getStackTrace();
            System.out.println(e.getCause());
            return ResponseEntity.badRequest().body("Error retrieving departments "+e.getMessage());
        }
    }

}
