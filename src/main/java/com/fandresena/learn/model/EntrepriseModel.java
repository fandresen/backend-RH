package com.fandresena.learn.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
public class EntrepriseModel {
 public EntrepriseModel(){  
    }

    private int id;
    @NotNull (message = "name cannot be null")
    @Size(min = 3,message = "min 3 characters")
    private String name;

    
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
        return "EntrepriseModel [id=" + id + ", name=" + name + "]";
    }

}
