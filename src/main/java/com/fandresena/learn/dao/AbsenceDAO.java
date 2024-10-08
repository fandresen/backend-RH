package com.fandresena.learn.dao;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Absence;
import com.fandresena.learn.model.AbsenceModel;
import com.fandresena.learn.repository.AbsenceRepo;
import com.fandresena.learn.repository.UserRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AbsenceDAO {

    private AbsenceRepo absenceRepo;
    private UserRepo userRepo;

    
    public void createAbsence (AbsenceModel absenceModel){
        Absence absence = new Absence();
        absence.setUser(userRepo.findById(absenceModel.getUser_id()).orElse(null));
        absence.setStartDate(absenceModel.getStartDate().toLocalDateTime());
        absence.setEndDate(absenceModel.getEndDate().toLocalDateTime());
        absence.setStatus(absenceModel.getStatus());
        absence.setType(absenceModel.getType());

        absenceRepo.save(absence);
    }

    public List<AbsenceModel> getAbsencebyDepartmentId(int id){
        List<Absence> absences = absenceRepo.getAllAbsencesByDepId(id);
        List<AbsenceModel> absenceModels = new ArrayList<>();
        for(int i=0; i<absences.size(); i++){
            Absence absence = absences.get(i);
            AbsenceModel absenceModel = new AbsenceModel();

            absenceModel.setId(absence.getId());
            absenceModel.setUser_id(absence.getUser().getId());
            absenceModel.setStartDate(absence.getStartDate().atOffset(ZoneOffset.UTC));
            absenceModel.setEndDate(absence.getEndDate().atOffset(ZoneOffset.UTC));
            absenceModel.setStatus(absence.getStatus());
            absenceModel.setType(absence.getType());

            absenceModels.add(absenceModel);
        }

        return absenceModels;
    }

    public List<AbsenceModel> getAbsencebyUserId(int id){
        List<Absence> absences = absenceRepo.getAllAbsencesByUserId(id);
        List<AbsenceModel> absenceModels = new ArrayList<>();
        for(int i=0; i<absences.size(); i++){
            Absence absence = absences.get(i);
            AbsenceModel absenceModel = new AbsenceModel();

            absenceModel.setId(absence.getId());
            absenceModel.setUser_id(absence.getUser().getId());
            absenceModel.setStartDate(absence.getStartDate().atOffset(ZoneOffset.UTC));
            absenceModel.setEndDate(absence.getEndDate().atOffset(ZoneOffset.UTC));
            absenceModel.setStatus(absence.getStatus());
            absenceModel.setType(absence.getType());

            absenceModels.add(absenceModel);
        }

        return absenceModels;
    }


}
