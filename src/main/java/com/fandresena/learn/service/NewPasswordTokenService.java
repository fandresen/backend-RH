package com.fandresena.learn.service;

import com.fandresena.learn.model.NewPasswordTokenModel;
import com.fandresena.learn.model.UserModel;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.NewPasswordTokenDAO;
import com.fandresena.learn.dao.UserDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NewPasswordTokenService {
    private NewPasswordTokenDAO newPasswordTokenDAO;
    private UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String createToken(UserModel user, String token) {
        logger.info("User ID here :" + user.getId());
        LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(10);
        try {
            NewPasswordTokenModel newtoken = newPasswordTokenDAO
                    .createToken(new NewPasswordTokenModel(token, expiredDate, user.getId()));
            return newtoken.getToken();
        } catch (Exception e) {
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

    public void deleteTokenByUserId(int user_id) {
        newPasswordTokenDAO.deleteTokenByUserId(user_id);
    }

    public UserModel findUser(String token) {
        NewPasswordTokenModel newToken = newPasswordTokenDAO.findTokenByToken(token);
        return userDAO.getUserById(newToken.getUser_id());

    }
}
