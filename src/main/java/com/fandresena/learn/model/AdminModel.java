package com.fandresena.learn.model;

public class AdminModel extends UserModel{
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String role;
    private String address;
    private String picture;
    private String password;
    private boolean in_Conger;

    public AdminModel(){
        super();
        role = "Admin";
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

    public boolean isIn_Conger() {
        return in_Conger;
    }

    public void setIn_Conger(boolean in_Conger) {
        this.in_Conger = in_Conger;
    }

    @Override
    public String toString() {
        return "AdminModel [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
                + ", phone_number=" + phone_number + ", role=" + role + ", address=" + address + ", picture=" + picture
                + ", password=" + password + ", in_Conger=" + in_Conger + "]";
    }



}
