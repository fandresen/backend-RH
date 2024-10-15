package com.fandresena.learn.model;

import java.time.LocalDateTime;


public class NotificationModel {

    private Long id;

    private int senderID;

    private int recipientId;

    private LocalDateTime notification_date;

    private String subject;

    private String data;

    private boolean read;

    public void setRead(boolean read) {
        this.read = read;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public boolean isRead() {
        return read;
    }
}
