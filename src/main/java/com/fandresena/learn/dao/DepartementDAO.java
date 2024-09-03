package com.fandresena.learn.dao;

import java.util.*;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Departement;
import com.fandresena.learn.entity.Entreprise;
import com.fandresena.learn.model.DepartementModel;
import com.fandresena.learn.repository.DepartementRepo;
import com.fandresena.learn.repository.EntrepriseRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DepartementDAO {
    private EntrepriseRepo entrepriseRepo;
    private DepartementRepo departementRepo;

    public void createDepartement(DepartementModel departementModel, int entrepriseId) {
        Departement departement = new Departement();
        departement.setId(departementModel.getId());
        departement.setName(departementModel.getName());

        Entreprise entreprise = entrepriseRepo.findById(entrepriseId).orElse(null);

        if (entreprise != null) {
            departement.setEntreprise(entreprise);
            departementRepo.save(departement);
        } else {
            throw new RuntimeException("Entreprise with Id : " + entrepriseId + "does not exist");
        }
    }

    public List<DepartementModel> getAllDepartementByEntreprise(int entrepriseId) {
        List<Departement> departements = departementRepo.getAllDepartmentsByEntrepriseId(entrepriseId);
        List<DepartementModel> departementModels = new ArrayList<>();
        for (Departement departement : departements) {
            DepartementModel departementModel = new DepartementModel();
            departementModel.setId(departement.getId());
            departementModel.setName(departement.getName());
            departementModel.setEntreprise_id(departement.getEntreprise().getId());
            departementModels.add(departementModel);
        }
        return departementModels;
    }

}
