package com.fandresena.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fandresena.learn.dao.EntrepriseDAO;
import com.fandresena.learn.model.EntrepriseModel;

@Component
public class CreateEntrepriseService {

    private EntrepriseDAO entrepriseDAO;
    @Autowired
    public CreateEntrepriseService(EntrepriseDAO entrepriseDAO) {
        this.entrepriseDAO = entrepriseDAO;
    }


    public void createEntreprise(EntrepriseModel entreprise){
        entrepriseDAO.creaEntreprise(entreprise);
    }
}
