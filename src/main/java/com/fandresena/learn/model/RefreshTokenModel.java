package com.fandresena.learn.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;


@Component
@NoArgsConstructor
public class RefreshTokenModel {
    private Long id;
    private String token;
    private LocalDateTime expirDate;

    public RefreshTokenModel(String token,LocalDateTime expirDate) {
        this.token = token;
        this.expirDate = expirDate;
    }
    // public RefreshTokenModel getRefreshToken() {
    //     RefreshTokenModel refreshToken = new RefreshTokenModel(id, token, expirDate);
    //     return refreshToken;
    // }

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