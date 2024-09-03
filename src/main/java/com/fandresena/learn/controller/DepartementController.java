package com.fandresena.learn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.DepartementDTO;
import com.fandresena.learn.dao.DepartementDAO;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/departement")
public class DepartementController {
    private DepartementDAO departementDAO;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartementDTO departementDTO){
        try{
            departementDAO.createDepartement(departementDTO.departement(), departementDTO.entreprise_Id());
            return ResponseEntity.ok("Department created successfully");
        }
        catch(Exception e){
            e.getStackTrace();
            return ResponseEntity.badRequest().body("Error creating department");
        }
    }
}
