package com.fandresena.learn.dao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.AccessToken;
import com.fandresena.learn.model.AccessTokenModel;
import com.fandresena.learn.repository.AccessTokenRepos;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccessTokenDAO {
    AccessTokenRepos AccessTokenRepo;

    public void putAccessToken(AccessTokenModel accessTokenModel) {
        AccessToken AccessToken = new AccessToken();
        AccessToken.setId(accessTokenModel.getId());
        AccessToken.setToken(accessTokenModel.getToken());
        AccessToken.setExpirDate(accessTokenModel.getExpirDate());

        AccessTokenRepo.save(AccessToken);
    }

    public AccessTokenModel getAccessTokenByToken(String token) {
        AccessToken AccessToken = AccessTokenRepo.findByToken(token);
        if (AccessToken != null) {
            AccessTokenModel AccessTokenModel = new AccessTokenModel(AccessToken.getToken(),
                    AccessToken.getExpirDate());
            return AccessTokenModel;
        }
        return null;
    }

    // delete Access token expired
    public void deleteExpiredAccessToken() {
        AccessTokenRepo.deleteAllByExpireDateBefore(LocalDateTime.now());
    }

    public void deleteAccessTokenByToken(String accessToken) {
        AccessTokenRepo.deleteByToken(accessToken);
    }
}
