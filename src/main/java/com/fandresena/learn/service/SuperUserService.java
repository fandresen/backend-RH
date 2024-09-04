package com.fandresena.learn.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.SuperUserDAO;
import com.fandresena.learn.model.SuperUserModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SuperUserService {

    private SuperUserDAO superUserDAO;
    private BCryptPasswordEncoder passwordEncoder; 


    public List<SuperUserModel> getAllSuperUsers() {
        return superUserDAO.getAllSuperuser();
    }

    public SuperUserModel getSuperUserById(int id) {
        return superUserDAO.getSuperUserById(id);
    }

    public void createSuperUser(SuperUserModel superUserModel) throws Exception { 
            String password = passwordEncoder.encode(superUserModel.getPassword());
            superUserModel.setPassword(password);
            superUserDAO.createSuperUser(superUserModel);
    }



}
