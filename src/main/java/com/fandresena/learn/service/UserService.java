package com.fandresena.learn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AdminModel;
import com.fandresena.learn.model.UserModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserModel> getAllUsers() {
        return userDAO.getAllUsersByRole("USER");
    }

    public UserModel getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void createUser(UserModel userModel) {
        String password = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(password);
        userDAO.createUser(userModel);
    }

    public void createAdmin(AdminModel adminModel) {
        String password = passwordEncoder.encode(adminModel.getPassword());
        adminModel.setPassword(password);
        userDAO.createAdmin(adminModel);
    }

    public List<UserModel> getByDepartementId(int id) {
        List<UserModel> users = userDAO.getAllUserByDepartementId(id);
        return users;
    }

    public List<UserModel> getAllByEntrepriseId(int entrepriseId) {
        List<UserModel> users = userDAO.getAllUsersByRole("USER");
        List<UserModel> entrepriseUsers = users.stream().filter(user -> user.getEntreprise_id() == entrepriseId)
                .collect(Collectors.toList());
        return entrepriseUsers;
    }

    public void changePassword(UserModel user, String password) {
        UserModel newuser = userDAO.findByEmail(user.getEmail());
        if (newuser!= null) {
            String encodedPassword = passwordEncoder.encode(password);
            newuser.setPassword(encodedPassword);
            userDAO.updateUser(newuser);
        }
    }
}
