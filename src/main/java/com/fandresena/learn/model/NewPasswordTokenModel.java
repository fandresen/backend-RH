package com.fandresena.learn.model;

import java.time.LocalDateTime;



public class NewPasswordTokenModel {
    private int id;
    private String token;
    private LocalDateTime expired_date;
    private int user_id;

    public NewPasswordTokenModel(String token, LocalDateTime expired_date, int user_id) {

        this.token = token;
        this.expired_date = expired_date;
        this.user_id = user_id;
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



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(LocalDateTime expired_date) {
        this.expired_date = expired_date;
    }

}
