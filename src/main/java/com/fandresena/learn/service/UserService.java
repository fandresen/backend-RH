package com.fandresena.learn.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.UserModel;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

      private UserDAO userDAO;
      private BCryptPasswordEncoder passwordEncoder; 

    public List<UserModel> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public UserModel getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void createUser(UserModel superUserModel) throws Exception { 
            String password = passwordEncoder.encode(superUserModel.getPassword());
            superUserModel.setPassword(password);  
            userDAO.createUser(superUserModel);
    }
    public List<UserModel> getByDepartementId(int id){
       List<UserModel> users = userDAO.getAllUserByDepartementId(id);
       return users;
    }
}
