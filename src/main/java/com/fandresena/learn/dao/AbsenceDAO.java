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

    public AbsenceModel createAbsence(AbsenceModel absenceModel) {

        Absence absence = new Absence();

        // Utiliser des setters pour définir les propriétés
        absence.setUser(userRepo.findById(absenceModel.getUser_id()).orElse(null));
        absence.setStartDate(absenceModel.getStartDate().toLocalDateTime());
        absence.setEndDate(absenceModel.getEndDate().toLocalDateTime());
        absence.setStatus(absenceModel.getStatus());
        absence.setType(absenceModel.getType());

        Absence savedAbsence = absenceRepo.save(absence);

        AbsenceModel newAbsenceModel = new AbsenceModel();

        newAbsenceModel.setId(savedAbsence.getId());
        newAbsenceModel.setUser_id(savedAbsence.getUser().getId());
        newAbsenceModel.setStartDate(savedAbsence.getStartDate().atOffset(ZoneOffset.UTC));
        newAbsenceModel.setEndDate(savedAbsence.getEndDate().atOffset(ZoneOffset.UTC));
        newAbsenceModel.setStatus(savedAbsence.getStatus());
        newAbsenceModel.setType(savedAbsence.getType());

        return newAbsenceModel;
    }

    public List<AbsenceModel> getAbsencebyDepartmentId(int id) {
        List<Absence> absences = absenceRepo.getAllAbsencesByDepId(id);
        List<AbsenceModel> absenceModels = new ArrayList<>();
        for (int i = 0; i < absences.size(); i++) {
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

    public List<AbsenceModel> getAbsencebyUserId(int id) {
        List<Absence> absences = absenceRepo.getAllAbsencesByUserId(id);
        List<AbsenceModel> absenceModels = new ArrayList<>();
        for (int i = 0; i < absences.size(); i++) {
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

    public AbsenceModel getAbsenceByID(int id) {
        Absence absence = absenceRepo.findById(id).orElse(null);
        AbsenceModel absenceModel = new AbsenceModel();

        absenceModel.setId(absence.getId());
        absenceModel.setUser_id(absence.getUser().getId());
        absenceModel.setStartDate(absence.getStartDate().atOffset(ZoneOffset.UTC));
        absenceModel.setEndDate(absence.getEndDate().atOffset(ZoneOffset.UTC));
        absenceModel.setStatus(absence.getStatus());
        absenceModel.setType(absence.getType());

        return absenceModel;
    }

    public void updateAbsence(AbsenceModel absence) {
        Absence currentAbsence = absenceRepo.findById(absence.getId()).orElse(null);
        if (currentAbsence != null) {
            currentAbsence.setUser(userRepo.findById(absence.getUser_id()).orElse(null));
            currentAbsence.setStartDate(absence.getStartDate().toLocalDateTime());
            currentAbsence.setEndDate(absence.getEndDate().toLocalDateTime());
            currentAbsence.setStatus(absence.getStatus());
            currentAbsence.setType(absence.getType());

            absenceRepo.save(currentAbsence);
        }
    }

}
