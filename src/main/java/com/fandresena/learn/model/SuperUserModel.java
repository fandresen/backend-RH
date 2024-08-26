package com.fandresena.learn.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SuperUserModel {
 public SuperUserModel() {
        
    }
    
    private int id;
    @NotNull(message = "email cannot be null")
    @Email(message = "should be a valid email address")
    private String email;
    @NotNull(message = "password cannot be null")
    private String password;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Superuser [id=" + id + ", email=" + email + ", password=" + password + "]";
    }
}
