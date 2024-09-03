package com.fandresena.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/entreprise")
@AllArgsConstructor
class EntrepriseController {

    private EntrepriseDAO entrepriseDAO;
    private UserDAO userDAO;

    // @Autowired
    // public EntrepriseController(EntrepriseDAO entrepriseDAO, UserDAO userDAO) {
    //     this.entrepriseDAO = entrepriseDAO;
    //     this.userDAO = userDAO;
    // }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createEntreprise(@Valid @RequestBody EntrepriseDTO data){
       
        // if(data.admin().getRole()== "ADMIN"){
        try{
            entrepriseDAO.creaEntreprise(data.entreprise());
            int entreprise_id = data.entreprise().getId();
            AdminModel admin = data.admin();
            admin.setEntreprise_id(entreprise_id);
            userDAO.createAdmin(admin);
            return ResponseEntity.ok("Entreprise created successfully"); 
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error creating entreprise");
        }
            
        // }
        // else{
        //     System.out.println("tsa mba nety");
        //     System.out.println(data.admin().getRole());
        //     return ResponseEntity.badRequest().build();
        // } 
       
       
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EntrepriseModel>> getAllEntreprises(){
        return ResponseEntity.ok(entrepriseDAO.getAllEntreprise());
    }
    @DeleteMapping
    public ResponseEntity<String> deleteEntreprise(@RequestParam("id") int id){
        entrepriseDAO.deleteEntreprise(id);
        return ResponseEntity.ok("Entreprise deleted successfully");
    }
}
