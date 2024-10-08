package com.fandresena.learn.controller;

import java.time.LocalDateTime;
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

import com.fandresena.learn.DTO.Status;
import com.fandresena.learn.dao.AbsenceDAO;
import com.fandresena.learn.dao.NotificationDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AbsenceModel;
import com.fandresena.learn.model.AdminModel;
import com.fandresena.learn.model.NotificationModel;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/absence")
@AllArgsConstructor
public class AbsenceController {
    AbsenceDAO absenceDAO;
    JWTService jwtService;
    UserDAO userDAO;
    NotificationService notificationService;

     private static final Logger logger = LoggerFactory.getLogger(AbsenceController.class);

    @GetMapping(path = "/department", produces = "application/json")
    public ResponseEntity<?> getAbsenceBysDepartement(@RequestParam(name = "departmentId") int departmentId) {
        try {
            List<AbsenceModel> absences = absenceDAO.getAbsencebyDepartmentId(departmentId);
            return ResponseEntity.ok(absences);
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
            absenceDAO.createAbsence(absence);

            //Envoyer une notification vers l'admin de l'entreprise
            List<UserModel> admins = userDAO.getAllUsersByRole("ADMIN");
            admins.forEach(admin -> {
                logger.info("ADMIN : "+ admin.getEntreprise_id());
                if(admin.getEntreprise_id() == user.getEntreprise_id()){
                    notificationService.createNotification(user.getId(), admin.getId(), "Demande une notification de type" + absence.getType());
                }
            });

            return ResponseEntity.ok("Absence inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Creating Absence " + e.getMessage());
        }
    }

}
