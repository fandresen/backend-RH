package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Entreprise;
import com.fandresena.learn.model.EntrepriseModel;
import com.fandresena.learn.repository.EntrepriseRepo;

@Component
public class EntrepriseDAO {
    @Autowired
    private EntrepriseRepo entrepriseRepo;

    public EntrepriseModel creaEntreprise(EntrepriseModel entrepriseModel) {
        Entreprise entreprise = new Entreprise();
        entreprise.setName(entrepriseModel.getName());
        Entreprise savedEntreprise = entrepriseRepo.save(entreprise);
        EntrepriseModel savedEntrepriseModel = new EntrepriseModel();
        savedEntrepriseModel.setName(savedEntreprise.getName());
        savedEntrepriseModel.setId(savedEntreprise.getId());
        return savedEntrepriseModel;
    }

    public EntrepriseModel getEntrepriseById(int id) {
        Entreprise entreprise = entrepriseRepo.findById(id).orElse(null);
        if (entreprise!= null) {
            EntrepriseModel entrepriseModel = new EntrepriseModel();
            entrepriseModel.setId(entreprise.getId());
            entrepriseModel.setName(entreprise.getName());
            return entrepriseModel;
        }
        else return null;
    }

    public List<EntrepriseModel> getAllEntreprise() {
        List<Entreprise> entrepriseList = entrepriseRepo.findAll();
        List<EntrepriseModel> entrepriseModelList = new ArrayList<>();
        for (int i = 0; i < entrepriseList.size(); i++) {
            EntrepriseModel entrepriseModel = new EntrepriseModel();
            entrepriseModel.setId(entrepriseList.get(i).getId());
            entrepriseModel.setName(entrepriseList.get(i).getName());
            entrepriseModelList.add(entrepriseModel);
        }
        return entrepriseModelList;
    }

    public void updateEntreprise(int id, EntrepriseModel entreprise) {
        // Entreprise entreprise =
        // entrepriseRepo.findById(entrepriseModel.getId()).orElse(null);
        Entreprise currentEntreprise = entrepriseRepo.findById(id).orElse(null);
        if (currentEntreprise != null) {
            currentEntreprise.setName(entreprise.getName());
            entrepriseRepo.save(currentEntreprise);
        }
    }

    public void deleteEntreprise(int id) {
        entrepriseRepo.deleteById(id);
    }

}
