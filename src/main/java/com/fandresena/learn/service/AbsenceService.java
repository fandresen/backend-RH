package com.fandresena.learn.service;

import org.springframework.stereotype.Service;

import com.fandresena.learn.DTO.AbsenceDTO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AbsenceModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AbsenceService {
    UserDAO userDAO;
    public AbsenceDTO convertToDTO(AbsenceModel absence){
        return new AbsenceDTO(absence,userDAO);
    }
}
