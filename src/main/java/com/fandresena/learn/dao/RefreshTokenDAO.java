package com.fandresena.learn.dao;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.RefreshToken;
import com.fandresena.learn.model.RefreshTokenModel;
import com.fandresena.learn.repository.RefreshTokenRepo;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RefreshTokenDAO {
    RefreshTokenRepo refreshTokenRepo;
    public void putRefreshToken(RefreshTokenModel refreshTokenModel){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(refreshTokenModel.getId());
        refreshToken.setToken(refreshTokenModel.getToken());
        refreshToken.setExpirDate(refreshTokenModel.getExpirDate());

        refreshTokenRepo.save(refreshToken);
    }

    public RefreshTokenModel getRefreshTokenByToken(String token){
        RefreshToken refreshToken = refreshTokenRepo.findByToken(token);
        if(refreshToken != null){
            RefreshTokenModel refreshTokenModel = new RefreshTokenModel(refreshToken.getToken(), refreshToken.getExpirDate());
            return refreshTokenModel;
        }
        return null;
    }

    //delete refresh token expired
    public void deleteExpiredRefreshToken(){
        refreshTokenRepo.deleteAllByExpireDateBefore(new Date());
    }

    public void deleteRefreshTokenByToken(String refreshToken){
        refreshTokenRepo.deleteByToken(refreshToken);
    }
}
