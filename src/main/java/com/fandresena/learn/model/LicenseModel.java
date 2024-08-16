package com.fandresena.learn.model;

import java.time.LocalDate;

public class LicenseModel {
    public LicenseModel() {

    }

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "License [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
    }
}
