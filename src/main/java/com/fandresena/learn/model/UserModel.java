package com.fandresena.learn.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.*;

public class UserModel implements UserDetails {
  public UserModel() {

    }

    private int id;
    @NotNull(message = "first name cannot be null")
    private String first_name;
    @NotNull(message = "last name cannot be null")
    private String last_name;
    @NotNull(message = "email cannot be null")
    @Email(message = "shoud be a valid email address")
    private String email;
    @NotNull(message = "phone number cannot be null")
    private String phone_number;
    @NotNull(message = "role cannot be null")
    private String role;
    @NotNull(message = "addresscannot be null")
    private String address;
    private String picture;
    @NotNull(message = "passwordcannot be null")
    private String password;
    private boolean in_Conger = false;
    private int departement_id;
    private boolean active = false;
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @NotNull(message = "entreprise_id cannot be null")
    private int entreprise_id;

     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public boolean getIsIn_Conger() {
        return in_Conger;
    }
    public void setIn_Conger(boolean in_Conger) {
        this.in_Conger = in_Conger;
    }
    public int getDepartement_id() {
        return departement_id;
    }
    public void setDepartement_id(int departement_id) {
        this.departement_id = departement_id;
    }
    public int getEntreprise_id() {
        return entreprise_id;
    }
    public void setEntreprise_id(int entreprise_id) {
        this.entreprise_id = entreprise_id;
    }
    @Override
    public String toString() {
        return "UserModel [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
                + ", phone_number=" + phone_number + ", role=" + role + ", address=" + address + ", picture=" + picture
                + ", password=" + password + ", in_Conger=" + in_Conger + ", departement_id=" + departement_id
                + ", entreprise_id=" + entreprise_id + "]";
    }


   
}
