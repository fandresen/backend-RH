package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fandresena.learn.entity.Absence;
import com.fandresena.learn.model.AbsenceModel;
import com.fandresena.learn.repository.AbsenceRepo;
import com.fandresena.learn.repository.UserRepo;

public class AbsenceDAO {

    @Autowired
    private AbsenceRepo absenceRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    AbsenceModel absenceModel;
    
    public void createAbsence (AbsenceModel absenceModel){
        Absence absence = new Absence();
        absence.setUser(userRepo.findById(absenceModel.getUser_id()).orElse(null));
        absence.setStartDate(absenceModel.getStartDate());
        absence.setEndDate(absenceModel.getEndDate());
        absence.setStatus(absenceModel.getStatus());
        absence.setType(absenceModel.getType());

        absenceRepo.save(absence);
    }

    public List<AbsenceModel> getAbsencebyId(int id){
        List<Absence> absences = absenceRepo.getAllAbsencesById(id);
        List<AbsenceModel> absenceModels = new ArrayList<>();
        for(int i=0; i<absences.size(); i++){
            Absence absence = absences.get(i);

            absenceModel.setId(absence.getId());
            absenceModel.setUser_id(absence.getUser().getId());
            absenceModel.setStartDate(absence.getStartDate());
            absenceModel.setEndDate(absence.getEndDate());
            absenceModel.setStatus(absence.getStatus());
            absenceModel.setType(absence.getType());

            absenceModels.add(absenceModel);
        }

        return absenceModels;
    }
}
