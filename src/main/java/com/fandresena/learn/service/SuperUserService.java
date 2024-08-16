package com.fandresena.learn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fandresena.learn.dao.SuperUserDAO;
import com.fandresena.learn.model.SuperUserModel;

@Component
public class SuperUserService {

    private final SuperUserDAO superUser;

    @Autowired
    public SuperUserService(SuperUserDAO superUser) {
        this.superUser = superUser;
    }

    public List<SuperUserModel> getAllSuperUsers() {
        return superUser.getAllSuperuser();
    }

    public SuperUserModel getSuperUserById(int id) {
        return superUser.getSuperUserById(id);
    }

    public void createSuperUser(SuperUserModel superUserModel) throws Exception { 
            superUser.createSuperUser(superUserModel);
    }



}
