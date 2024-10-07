package com.fandresena.learn.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.EntrepriseDTO;
import com.fandresena.learn.dao.EntrepriseDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AdminModel;
import com.fandresena.learn.model.EntrepriseModel;
import com.fandresena.learn.service.TokenGeneratorService;
import com.fandresena.learn.service.NewPasswordTokenService;
import com.fandresena.learn.service.SendEmailService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/entreprise")
@AllArgsConstructor
class EntrepriseController {

    private EntrepriseDAO entrepriseDAO;
    private NewPasswordTokenService newPasswordTokenService;
    private UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(EntrepriseController.class);

    @PreAuthorize("hasAuthority('SUP_USER')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createEntreprise(@Valid @RequestBody EntrepriseDTO data) {

        try {
            EntrepriseModel entrepriseModel = entrepriseDAO.creaEntreprise(data.entreprise());
            int entreprise_id = entrepriseModel.getId();
            AdminModel admin = data.admin();
             // Insert entreprise id in Admin so that admin.entreprise won't be null
             admin.setEntreprise_id(entreprise_id);

             AdminModel newAdmin = userDAO.createAdmin(data.admin());

            // Generate newPasswordToken
            String token = TokenGeneratorService.generatepassword(12);
            String realToken = newPasswordTokenService.createToken(newAdmin, token);
            String template = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/CreateNewPassword.html")));
            SendEmailService.sendEmail(admin.getEmail(), "Compte ZenRH", admin.getFirst_name() , " http://192.168.1.87:5173/newPassword?tkn="+realToken,template);

           
            return ResponseEntity.ok("Entreprise created successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating entreprise");
        }

    }

    @PreAuthorize("hasAuthority('SUP_USER')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EntrepriseModel>> getAllEntreprises() {
        List<EntrepriseModel> entreprises = entrepriseDAO.getAllEntreprise();
        return ResponseEntity.ok(entreprises);
    }

    @PreAuthorize("hasAuthority('SUP_USER')")
    @DeleteMapping
    public ResponseEntity<String> deleteEntreprise(@RequestParam("id") int id) {
        entrepriseDAO.deleteEntreprise(id);
        return ResponseEntity.ok("Entreprise deleted successfully");
    }
}
