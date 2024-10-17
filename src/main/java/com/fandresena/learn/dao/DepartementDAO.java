package com.fandresena.learn.dao;

import java.util.*;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Departement;
import com.fandresena.learn.entity.Entreprise;
import com.fandresena.learn.entity.Users;
import com.fandresena.learn.model.DepartementModel;
import com.fandresena.learn.repository.DepartementRepo;
import com.fandresena.learn.repository.EntrepriseRepo;
import com.fandresena.learn.repository.UserRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DepartementDAO {
    EntrepriseRepo entrepriseRepo;
    DepartementRepo departementRepo;
    UserRepo userRepo;

    public DepartementModel createDepartement(DepartementModel departementModel, int entrepriseId) {
        Departement departement = new Departement();
        departement.setId(departementModel.getId());
        departement.setName(departementModel.getName());
        Users chef = userRepo.findById(departementModel.getChef_id()).orElse(null);
        if(chef != null){
            departement.setChef(chef);
        }
        Entreprise entreprise = entrepriseRepo.findById(entrepriseId).orElse(null);

        if (entreprise != null) {
            departement.setEntreprise(entreprise);
            departementRepo.save(departement);
            DepartementModel model = this.converToModel(departement);
            return model;
        } else {
            throw new RuntimeException("Entreprise with Id : " + entrepriseId + "does not exist");
        }
    }

    public DepartementModel updateDepartement(DepartementModel departementModel) {
        Departement departement = departementRepo.findById(departementModel.getId()).orElse(null);
        if (departement!= null) {
            departement.setId(departementModel.getId());
            departement.setName(departementModel.getName());
            Users chef = userRepo.findById(departementModel.getChef_id()).orElse(null);
            if(chef!= null){
                departement.setChef(chef);
            }
            departement.setEntreprise(entrepriseRepo.findById(departementModel.getEntreprise_id()).orElse(null));


            departementRepo.save(departement);
            return this.converToModel(departement);
        } else {
            throw new RuntimeException("Departement with Id : " + departementModel.getId() + "does not exist");
        }
    }

    public DepartementModel converToModel(Departement departement){
        DepartementModel model = new DepartementModel();
        model.setId(departement.getId());
        model.setName(departement.getName());
        if(departement.getChef() != null){
            model.setChef_id(departement.getChef().getId());
        }
        model.setEntreprise_id(departement.getEntreprise().getId());
        
        return model;      
    }

    public List<DepartementModel> getAllDepartementByEntreprise(int entrepriseId) {
        List<Departement> departements = departementRepo.getAllDepartmentsByEntrepriseId(entrepriseId);
        List<DepartementModel> departementModels = new ArrayList<>();
        for (Departement departement : departements) {
            DepartementModel departementModel = new DepartementModel();
            departementModel.setId(departement.getId());
            departementModel.setName(departement.getName());
            departementModel.setEntreprise_id(departement.getEntreprise().getId());
            if(departement.getChef()!= null){
                departementModel.setChef_id(departement.getChef().getId());
            }
            departementModels.add(departementModel);
        }
        return departementModels;
    }

    public DepartementModel getDepartementById(int id) {
        Departement departement = departementRepo.findById(id).orElse(null);
        if (departement != null) {
            DepartementModel departementModel = new DepartementModel();
            departementModel.setId(departement.getId());
            departementModel.setName(departement.getName());
            departementModel.setEntreprise_id(departement.getEntreprise().getId());
            departementModel.setChef_id(departement.getChef().getId());
            return departementModel;
        } else
            return null;
    }

}
