package com.fandresena.learn.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SuperUserModel implements UserDetails {
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

    @Override
    public Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("SUP_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
