package com.fandresena.learn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receipient_id", nullable = false)
    private Users receipientId;

    private LocalDateTime notification_date;

    private String subject;

    private String data;

    private boolean is_read;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
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

    public Users getReceipientId() {
        return receipientId;
    }

    public void setReceipientId(Users receipientId) {
        this.receipientId = receipientId;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean read) {
        this.is_read = read;
    }

}
