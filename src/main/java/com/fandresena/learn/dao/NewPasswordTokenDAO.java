package com.fandresena.learn.dao;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.NewPasswordToken;
import com.fandresena.learn.entity.Users;
import com.fandresena.learn.model.NewPasswordTokenModel;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.repository.EntrepriseRepo;
import com.fandresena.learn.repository.NewPasswordTokenRepo;
import com.fandresena.learn.repository.UserRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NewPasswordTokenDAO {
    private NewPasswordTokenRepo newPasswordTokenRepo;
    private EntrepriseRepo entrepriseRepo;
    private UserRepo userRepo;

    public NewPasswordTokenModel createToken(NewPasswordTokenModel tokenModel, UserModel user) {

        NewPasswordToken passwordToken = new NewPasswordToken();
        passwordToken.setToken(tokenModel.getToken());
        passwordToken.setExpired_date(tokenModel.getExpired_date());

        Users userExist = userRepo.findById(user.getId()).orElse(null);
        if(userExist == null){
            Users newUser = new Users();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setFirst_name(user.getFirst_name());
            newUser.setLast_name(user.getLast_name());
            newUser.setPhone_number(user.getPhone_number());
            newUser.setRole(user.getRole());
            newUser.setAddress(user.getAddress());
            newUser.setPicture(user.getPicture());
            newUser.setPassword(user.getPassword());
            newUser.setIn_Conger(user.getIsIn_Conger());
            newUser.setActive(user.isActive());
            newUser.setEntreprise(entrepriseRepo.findById(user.getEntreprise_id()).orElse(null));
            user.setId(newUser.getId());
            newUser = userRepo.save(newUser);
            passwordToken.setUser(newUser); 
        }
        else{
            passwordToken.setUser(userExist);
        }
        
       

        try {
            NewPasswordToken newPasswordToken = newPasswordTokenRepo.save(passwordToken);
            return new NewPasswordTokenModel(newPasswordToken.getToken(),
                    newPasswordToken.getExpired_date(), newPasswordToken.getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public NewPasswordTokenModel findTokenByToken(String token) {
        NewPasswordToken passtoken = newPasswordTokenRepo.findByToken(token);
        NewPasswordTokenModel tokenModel = new NewPasswordTokenModel(passtoken.getToken(),
                passtoken.getExpired_date(), passtoken.getUser().getId());
        return tokenModel;
    }

    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        newPasswordTokenRepo.deleteAllByExpireDateBefore(now);
    }

    @Transactional
    public void deleteTokenByUserId(int user_id) {
        NewPasswordToken passtoken = newPasswordTokenRepo.findByUser_id(user_id);
        if(passtoken!= null){
            newPasswordTokenRepo.delete(passtoken);
        }
    }

    public UserModel findUserByToken(String token){
        NewPasswordToken passtoken = newPasswordTokenRepo.findByToken(token);
        Users user = passtoken.getUser();
        UserModel userModel = new UserModel();
        userModel.setFirst_name(user.getFirst_name());
        userModel.setLast_name(user.getLast_name());
        userModel.setEmail(user.getEmail());
        userModel.setPhone_number(user.getPhone_number());
        userModel.setRole(user.getRole());
        userModel.setAddress(user.getAddress());
        userModel.setId(user.getId());
        userModel.setPicture(user.getPicture());
        userModel.setPassword(user.getPassword());
        userModel.setIn_Conger(user.getIsIn_Conger());
        userModel.setActive(user.isActive());

        return userModel;
    }
}
