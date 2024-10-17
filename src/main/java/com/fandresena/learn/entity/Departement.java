package com.fandresena.learn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Departement {
    public Departement(){
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;
    @JoinColumn(name = "dep_chef_id")
    @ManyToOne
    private Users chef;
    
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
    public Entreprise getEntreprise() {
        return entreprise;
    }
    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
    public Users getChef() {
        return chef;
    }
    public void setChef(Users chef) {
        this.chef = chef;
    }
}
