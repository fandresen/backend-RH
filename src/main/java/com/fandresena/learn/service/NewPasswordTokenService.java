package com.fandresena.learn.service;

import com.fandresena.learn.model.NewPasswordTokenModel;
import com.fandresena.learn.model.UserModel;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.NewPasswordTokenDAO;
import com.fandresena.learn.dao.UserDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewPasswordTokenService {
    private NewPasswordTokenDAO newPasswordTokenDAO;
    private UserDAO userDAO;

    public String createToken(UserModel user,String token) {
        LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(10);
        try{
         NewPasswordTokenModel newtoken = newPasswordTokenDAO.createToken(new NewPasswordTokenModel(token, expiredDate,user.getId()),user);
            return newtoken.getToken();
        }
        catch(Exception e){
            e.getStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean isvallidToken(String token) {
        NewPasswordTokenModel tokenModel = newPasswordTokenDAO.findTokenByToken(token);
        if (tokenModel == null || tokenModel.getExpired_date().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    public void deleteExpiredTokens() {
        newPasswordTokenDAO.deleteExpiredTokens();
    }

    public UserModel findUser(String token) {
        NewPasswordTokenModel newToken = newPasswordTokenDAO.findTokenByToken(token);
        return userDAO.getUserById(newToken.getUser_id());
        
    }
}
