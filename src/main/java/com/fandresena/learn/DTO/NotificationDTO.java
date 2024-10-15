package com.fandresena.learn.DTO;

import java.time.LocalDateTime;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.NotificationModel;

public class NotificationDTO {
    private Long id;
    private int senderID;
    private int recipientId;
    private LocalDateTime notification_date;
    private String subject;
    private String senderName;
    private String senderProfilePictureUrl;
    private String data;
    private boolean read;

    public NotificationDTO(UserDAO userDAO, NotificationModel notificationModel) {
        this.id = notificationModel.getId();
        this.senderID = notificationModel.getSenderID();
        this.recipientId = notificationModel.getRecipientId();
        this.notification_date = notificationModel.getNotification_date();
        this.subject = notificationModel.getSubject();
        this.data = notificationModel.getData();
        this.read = notificationModel.isRead();

        this.senderName = userDAO.getUserById(senderID).getName();
        this.senderProfilePictureUrl = userDAO.getUserById(senderID).getPicture();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public LocalDateTime getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(LocalDateTime notification_date) {
        this.notification_date = notification_date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderProfilePictureUrl() {
        return senderProfilePictureUrl;
    }

    public void setSenderProfilePictureUrl(String senderProfilePictureUrl) {
        this.senderProfilePictureUrl = senderProfilePictureUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
