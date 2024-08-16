package com.fandresena.learn.model;

import java.time.LocalDate;

public class AbsenceModel {

    private int id;  
    private int user_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String type;

    public AbsenceModel() {
       }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AbsenceModel [id=" + id + ", user_id=" + user_id + ", startDate=" + startDate + ", endDate=" + endDate
                + ", status=" + status + ", type=" + type + "]";
    }

}
