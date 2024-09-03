package com.fandresena.learn.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class AccessTokenModel {
    private Long id;
    private String token;
    private LocalDateTime expirDate;

    public AccessTokenModel(String token, LocalDateTime expirDate) {
        this.token = token;
        this.expirDate = expirDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(LocalDateTime expirDate) {
        this.expirDate = expirDate;
    }

}
