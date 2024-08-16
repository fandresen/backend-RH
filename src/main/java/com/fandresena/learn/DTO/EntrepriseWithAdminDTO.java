package com.fandresena.learn.DTO;

import com.fandresena.learn.model.EntrepriseModel;
import com.fandresena.learn.model.UserModel;

public class EntrepriseWithAdminDTO {
    private EntrepriseModel entreprise;
    private UserModel user;
    
    public EntrepriseModel getEntreprise() {
        return entreprise;
    }
    public void setEntreprise(EntrepriseModel entreprise) {
        this.entreprise = entreprise;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }

    
}
