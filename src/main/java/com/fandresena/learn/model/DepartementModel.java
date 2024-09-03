package com.fandresena.learn.model;

import jakarta.validation.constraints.NotNull;

public class DepartementModel {

    private int id;
    @NotNull(message = "Departement name cannot be null")
    private String name;
    @NotNull(message = "entreprise_id cannot be null")
    private int entreprise_id;

    public DepartementModel() {
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

    public int getEntreprise_id() {
        return entreprise_id;
    }

    public void setEntreprise_id(int entreprise_id) {
        this.entreprise_id = entreprise_id;
    }

    @Override
    public String toString() {
        return "DepartementModel [id=" + id + ", name=" + name + ", entreprise_id=" + entreprise_id + "]";
    }

}
