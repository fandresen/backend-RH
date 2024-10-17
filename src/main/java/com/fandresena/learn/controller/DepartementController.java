package com.fandresena.learn.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.DepartementRequestDTO;
import com.fandresena.learn.DTO.DepartementResponsetDTO;
import com.fandresena.learn.DTO.UserDTO;
import com.fandresena.learn.dao.DepartementDAO;
import com.fandresena.learn.model.DepartementModel;
import com.fandresena.learn.model.UserModel;
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
    private static final Logger logger = LoggerFactory.getLogger(DepartementController.class);

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartementRequestDTO departementDTO,
            HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        DepartementModel departementModel = departementDTO.convertToModel();
        departementModel.setEntreprise_id(entrepriseId);
        UserModel request_user = departementDTO.user();
        try {
            DepartementModel new_departement = departementDAO.createDepartement(departementModel,
                    departementModel.getEntreprise_id()); // create departement without chef
            request_user.setDepartement_id(new_departement.getId());
            UserModel userModel = userService.createEntireuser(request_user, token); // create user chef
            new_departement.setChef_id(userModel.getId());
            departementDAO.updateDepartement(new_departement);
            return ResponseEntity.ok("Department created successfully");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error(e.getLocalizedMessage());
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
            List<DepartementResponsetDTO> all_dep = new ArrayList<DepartementResponsetDTO>();
            for (DepartementModel departement : departements) {
                logger.info("CHEF ID :"+departement.getChef_id());
                UserModel chef = userService.getUserById(departement.getChef_id());
                UserDTO chefDTO = userService.convertToDTO(chef);
                all_dep.add(new DepartementResponsetDTO(departement, chefDTO,
                        userService.getByDepartementId(departement.getId()).size()));
            }
            return ResponseEntity.ok(all_dep);

        } catch (Exception e) {
            // e.getStackTrace();
            return ResponseEntity.badRequest().body("Error retrieving departments " + e.getMessage());
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDepartementById(@PathVariable int id, HttpServletRequest res) {
        String token = res.getHeader("Authorization");
        token = token.substring(7);
        int entrepriseId = jwtService.extractEntrepriseId(token);
        try {
            if (id == 0) {
                List<DepartementModel> departements = departementDAO.getAllDepartementByEntreprise(entrepriseId);
                for (DepartementModel departement : departements) {
                    UserModel chef = userService.getUserById(departement.getChef_id());
                    UserDTO chefDTO = userService.convertToDTO(chef);
                    DepartementResponsetDTO dpResponsetDTO = new DepartementResponsetDTO(departement, chefDTO,
                            userService.getByDepartementId(departement.getId()).size());
                    return ResponseEntity.ok(dpResponsetDTO);
                }
            }
            DepartementModel departement = departementDAO.getDepartementById(id);
            UserModel chef = userService.getUserById(departement.getChef_id());
            UserDTO chefDTO = userService.convertToDTO(chef);
            DepartementResponsetDTO dpResponsetDTO = new DepartementResponsetDTO(departement, chefDTO,
                    userService.getByDepartementId(departement.getId()).size());
            return ResponseEntity.ok(dpResponsetDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving department " + e.getMessage());
        }
    }

}
