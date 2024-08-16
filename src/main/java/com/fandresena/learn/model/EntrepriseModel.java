package com.fandresena.learn.model;


public class EntrepriseModel {
 public EntrepriseModel(){  
    }

    private int id;
    private String name;
    private int license_id;

    public int getLicense_id() {
        return license_id;
    }
    public void setLicense_id(int license_id) {
        this.license_id = license_id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "EntrepriseModel [id=" + id + ", name=" + name + ", license_id=" + license_id + "]";
    }

}
