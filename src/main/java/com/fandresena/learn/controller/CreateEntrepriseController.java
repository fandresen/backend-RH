package com.fandresena.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.EntrepriseWithAdminDTO;
import com.fandresena.learn.dao.EntrepriseDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.EntrepriseModel;

@RestController
@RequestMapping("/create-entreprise")
class CreateEntrepriseController {

    private EntrepriseDAO entrepriseDAO;
    private UserDAO userDAO;

    @Autowired
    public CreateEntrepriseController(EntrepriseDAO entrepriseDAO, UserDAO userDAO) {
        this.entrepriseDAO = entrepriseDAO;
        this.userDAO = userDAO;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createEntreprise(@RequestBody EntrepriseWithAdminDTO data){
       
        if(data.getUser().getRole()== "ADMIN"){
            entrepriseDAO.creaEntreprise(data.getEntreprise());
            userDAO.createUser(data.getUser());
            return ResponseEntity.ok("Entreprise created successfully"); 
        }
        else return ResponseEntity.badRequest().build();
       
       
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EntrepriseModel>> getAllEntreprises(){
        return ResponseEntity.ok(entrepriseDAO.getAllEntreprise());
    }

}
