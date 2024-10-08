package com.fandresena.learn.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.dao.NotificationDAO;
import com.fandresena.learn.model.NotificationModel;
import com.fandresena.learn.service.JWTService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {
    NotificationDAO notificationDAO;
    JWTService jwtService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createNotification(NotificationModel notificationModel) {
        try{
            notificationDAO.createNotification(notificationModel);
            return ResponseEntity.ok("Notification Created successFully");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }

    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getNotificationById(HttpServletRequest res){
        String token = res.getHeader("Authorization").substring(7);
        int userId = jwtService.extractUserId(token);
        try{
            List<NotificationModel> notifications = notificationDAO.getNotificationByUserId(userId);
            return ResponseEntity.ok(notifications);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }
    }

}
