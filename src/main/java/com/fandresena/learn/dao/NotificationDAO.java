package com.fandresena.learn.dao;

import java.util.*;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Notification;
import com.fandresena.learn.model.NotificationModel;
import com.fandresena.learn.repository.NotificationRepo;
import com.fandresena.learn.repository.UserRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NotificationDAO {
    NotificationRepo notificationRepo;
    UserRepo userRepo;

    public void createNotification(NotificationModel notificationModel) {
        Notification notification = new Notification();
        notification.setId(notificationModel.getId());
        notification.setNotification_date(notificationModel.getNotification_date());
        notification.setSender(userRepo.findById(notificationModel.getSenderID()).orElse(null));
        notification.setReceipientId(userRepo.findById(notificationModel.getRecipientId()).orElse(null));
        notification.setSubject(notificationModel.getSubject());

        notificationRepo.save(notification);
    }

    public List<NotificationModel> getNotificationByUserId(int userId) {
        List<Notification> notifications = notificationRepo.findAllByReceipient_Id(userId);
        List<NotificationModel> notificationModels = new ArrayList<NotificationModel>();
        for (Notification notification : notifications) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setId(notification.getId());
            notificationModel.setNotification_date(notification.getNotification_date());
            notificationModel.setSenderID(notification.getSender().getId());
            notificationModel.setRecipientId(notification.getReceipientId().getId());
            notificationModel.setSubject(notification.getSubject());
            notificationModels.add(notificationModel);
        }
        return notificationModels;
    }
}
