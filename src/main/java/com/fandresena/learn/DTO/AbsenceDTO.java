package com.fandresena.learn.DTO;

import java.time.OffsetDateTime;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AbsenceModel;

public class AbsenceDTO {

    private int id;
    private String user_name; 
    private OffsetDateTime startDate;
    private  OffsetDateTime endDate;
    private Status status;
    private String type;

    public AbsenceDTO(AbsenceModel absenceModel,UserDAO userDAO) {
        this.id = absenceModel.getId();
        this.startDate = absenceModel.getStartDate();
        this.endDate = absenceModel.getEndDate();
        this.status = absenceModel.getStatus();
        this.type = absenceModel.getType();
        this.user_name = userDAO.getUserById(absenceModel.getUser_id()).getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
