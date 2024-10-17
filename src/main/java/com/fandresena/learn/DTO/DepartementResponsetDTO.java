package com.fandresena.learn.DTO;

import com.fandresena.learn.model.DepartementModel;

public record DepartementResponsetDTO(DepartementModel departementModel,UserDTO responsable,int numberOfEmployees) {
}