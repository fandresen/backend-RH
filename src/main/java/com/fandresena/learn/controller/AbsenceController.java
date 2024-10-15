package com.fandresena.learn.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.DTO.AbsenceDTO;
import com.fandresena.learn.DTO.Status;
import com.fandresena.learn.dao.AbsenceDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AbsenceModel;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.AbsenceService;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/absence")
@AllArgsConstructor
public class AbsenceController {
    AbsenceDAO absenceDAO;
    AbsenceService absenceService;
    JWTService jwtService;
    UserDAO userDAO;
    NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(AbsenceController.class);

    @GetMapping(path = "/department", produces = "application/json")
    public ResponseEntity<?> getAbsenceByDepartement(HttpServletRequest res) {
        String token = res.getHeader("Authorization").substring(7);
        try {
            int departmentId = jwtService.extractDepartementId(token);
            List<AbsenceModel> absences = absenceDAO.getAbsencebyDepartmentId(departmentId);
            List<AbsenceDTO> absencesDTO = new ArrayList<AbsenceDTO>();
            for (AbsenceModel absence : absences) {
                AbsenceDTO absenceDTO = absenceService.convertToDTO(absence);
                absencesDTO.add(absenceDTO);
            }
            return ResponseEntity.ok(absencesDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error" + e.getMessage());
        }

    }

    @PostMapping()
    public ResponseEntity<?> newAbsence(@RequestBody AbsenceModel absence, HttpServletRequest res) {

        String token = res.getHeader("Authorization").substring(7);

        try {
            String userEmail = jwtService.extractUserEmail(token);
            UserModel user = userDAO.findByEmail(userEmail);
            absence.setUser_id(user.getId());
            absence.setStatus(Status.EN_COURS);
            AbsenceModel newAbsence = absenceDAO.createAbsence(absence);

            try {
                // Envoyer une notification vers le chef de departemnt
                List<UserModel> depChefs = userDAO.getAllUsersByRole("CHEF_DEP");
                depChefs.forEach(depChef -> {
                    if (depChef.getDepartement_id() == user.getDepartement_id()) {
                        notificationService.createNotification(user.getId(), depChef.getId(),
                                "Demande une notification de type " + absence.getType(),
                                "/absence-validation?absenceId=" + newAbsence.getId());
                    }
                });

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            return ResponseEntity.ok("Absence inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Creating Absence " + e.getMessage());
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAbsenceById(@RequestParam(name = "id") int id) {
        try {
            AbsenceModel absence = absenceDAO.getAbsenceByID(id);
            AbsenceDTO absenceDTO = absenceService.convertToDTO(absence);
            return ResponseEntity.ok(absenceDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error" + e.getMessage());
        }

    }

    @PostMapping(path = "/updateStatus")
    public ResponseEntity<?> updateAbsenceStatus(@RequestParam(name = "absenceID") int absenceID,
            @RequestParam(name = "action") String action) {
        try {
            AbsenceModel absence = absenceDAO.getAbsenceByID(absenceID);

            if ("approve".equalsIgnoreCase(action)) {
                absence.setStatus(Status.APPROUVE);
                // Envoyer une notification vers l'employee qui a demander l'absence
                UserModel user = userDAO.getUserById(absence.getUser_id());
                notificationService.createNotification(user.getId(), absence.getUser_id(),
                        "Demande d'absence approuvée", "/");
            } else if ("reject".equalsIgnoreCase(action)) {
                absence.setStatus(Status.REJECTEE);
            } else {
                return ResponseEntity.badRequest().body("Invalid action specified. Use 'approve' or 'reject'.");
            }

            absenceDAO.updateAbsence(absence);
            return ResponseEntity.ok("Absence updated successfully with status: " + absence.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating Absence: " + e.getMessage());
        }
    }

}
