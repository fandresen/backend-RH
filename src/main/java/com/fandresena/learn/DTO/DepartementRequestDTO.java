package com.fandresena.learn.DTO;

import com.fandresena.learn.model.DepartementModel;
import com.fandresena.learn.model.UserModel;

public record DepartementRequestDTO(String departement_name, UserModel user) {
    public DepartementModel convertToModel() {
        DepartementModel model = new DepartementModel();
        model.setName(departement_name);
        return model;
    }
}


