package com.fandresena.learn.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.NotificationDAO;
import com.fandresena.learn.model.NotificationModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    NotificationDAO notificationDAO;

    public void createNotification(int senderId, int receipientId, String subject) {

        NotificationModel notification = new NotificationModel();
        notification.setRecipientId(receipientId);
        notification.setSenderID(senderId);
        notification.setNotification_date(LocalDateTime.now());
        notification.setSubject(subject);

        notificationDAO.createNotification(notification);
    }
}
