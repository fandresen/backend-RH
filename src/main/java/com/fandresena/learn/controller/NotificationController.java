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

import com.fandresena.learn.DTO.NotificationDTO;
import com.fandresena.learn.dao.NotificationDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.NotificationModel;
import com.fandresena.learn.service.JWTService;
import com.fandresena.learn.service.NotificationService;
import com.fandresena.learn.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    NotificationDAO notificationDAO;
    NotificationService notificationService;
    JWTService jwtService;
    UserDAO userDAO;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createNotification(@RequestBody NotificationModel notificationModel) {
        try {
            notificationDAO.createNotification(notificationModel);
            return ResponseEntity.ok("Notification Created successFully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }

    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getNotificationById(@RequestParam(name = "lastNotifId") int lastNotifId,
            HttpServletRequest res) {
        String token = res.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try {
            List<NotificationModel> notifications = notificationDAO.getNotificationByUserId(userId);
            for (NotificationModel notification : notifications) {
                if (lastNotifId == 0) {
                    NotificationDTO notificationDTO = notificationService.converToDTO(notification, userDAO);
                    return ResponseEntity.ok(notificationDTO);
                } else if (lastNotifId != 0 && notification.getId() > lastNotifId) {
                    NotificationDTO notificationDTO = notificationService.converToDTO(notification, userDAO);
                    return ResponseEntity.ok(notificationDTO);
                }
            }
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<?> getPastNotification(HttpServletRequest res) {
        String token = res.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try {
            List<NotificationModel> notifications = notificationDAO.getNotificationByUserId(userId);
            List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
            for (NotificationModel notification : notifications) {
                NotificationDTO notificationDTO = notificationService.converToDTO(notification, userDAO);
                notificationDTOs.add(notificationDTO);
            }
            return ResponseEntity.ok(notificationDTOs);
        } catch (Exception e) {
            logger.error("error : " + e.toString());
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }
 
    }

}
