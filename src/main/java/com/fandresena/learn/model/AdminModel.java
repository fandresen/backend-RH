package com.fandresena.learn.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
public class AdminModel extends UserModel {
    private int id;
    @NotNull(message = "first name cannot be null")
    @Size(min = 3, message = "min required 3 chracters")
    private String first_name;
    @NotNull(message = "last name cannot be null")
    @Size(min = 3, message = "min required 3 chracters")
    private String last_name;
    @NotNull(message = "email cannot be null")
    @Email(message = "should be a valid email address")
    private String email;
    @NotNull(message = "phone number cannot be null")
    @Size(min = 10, message = "phone number must be from 10")
    private String phone_number;
    private String role;
    @NotNull(message = "adress cannot be null")
    @Size(min = 5, message = "adress require 5 characters min")
    private String adress;
    private String picture;
    @NotNull(message = "passwprd cannot be null")
    @Size(min = 10)
    private String password;
    @NotNull(message = "in_conger cannot be null")
    private boolean in_Conger;
    @NotNull(message = "entreprise cannot be null")
    private int entreprise_id;

    public int getEntreprise_id() {
        return entreprise_id;
    }

    public void setEntreprise_id(int entreprise_id) {
        this.entreprise_id = entreprise_id;
    }

    public AdminModel() {
        super();
        role = "ADMIN";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String address) {
        this.adress = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIn_Conger() {
        return in_Conger;
    }

    public void setIn_Conger(boolean in_Conger) {
        this.in_Conger = in_Conger;
    }

    @Override
    public String toString() {
        return "AdminModel [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
                + ", phone_number=" + phone_number + ", role=" + role + ", address=" + adress + ", picture=" + picture
                + ", password=" + password + ", in_Conger=" + in_Conger + "]";
    }

}
