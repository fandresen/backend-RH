package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fandresena.learn.entity.Users;
import com.fandresena.learn.model.AdminModel;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.repository.DepartementRepo;
import com.fandresena.learn.repository.EntrepriseRepo;
import com.fandresena.learn.repository.UserRepo;
import com.fandresena.learn.service.UserService;

import jakarta.transaction.Transactional;

@Component
public class UserDAO {
       private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private DepartementRepo departementRepository;

    @Autowired
    private EntrepriseRepo entrepriseRepository;

    public void createUser(UserModel userModel) {
        Users exist_user = userRepository.findByEmail(userModel.getEmail()).orElse(null);
        if (exist_user == null) {
            Users user = new Users();
            user.setId(userModel.getId());
            user.setFirst_name(userModel.getFirst_name());
            user.setLast_name(userModel.getLast_name());
            user.setEmail(userModel.getEmail());
            user.setPhone_number(userModel.getPhone_number());
            user.setRole(userModel.getRole());
            user.setAddress(userModel.getAddress());
            user.setDepartement(departementRepository.findById(userModel.getDepartement_id()).orElse(null));
            user.setEntreprise(entrepriseRepository.findById(userModel.getEntreprise_id()).orElse(null));
            user.setPicture(userModel.getPicture());
            user.setActive(userModel.isActive());
            user.setPassword(userModel.getPassword());
            user.setIn_Conger(userModel.getIsIn_Conger());

            userRepository.save(user);
        }

    }

    public void createAdmin(AdminModel adminModel) {
        Users exist_user = userRepository.findByEmail(adminModel.getEmail()).orElse(null);
        if (exist_user == null) {
            Users user = new Users();
            user.setId(adminModel.getId());
            user.setFirst_name(adminModel.getFirst_name());
            user.setLast_name(adminModel.getLast_name());
            user.setEmail(adminModel.getEmail());
            user.setPhone_number(adminModel.getPhone_number());
            user.setRole(adminModel.getRole());
            user.setAddress(adminModel.getAddress());
            user.setEntreprise(entrepriseRepository.findById(adminModel.getEntreprise_id()).orElse(null));
            user.setPicture(adminModel.getPicture());
            user.setPassword(adminModel.getPassword());
            user.setActive(adminModel.isActive());
            user.setIn_Conger(adminModel.getIsIn_Conger());

            userRepository.save(user);
        }

    }


    public UserModel getUserById(int id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setFirst_name(user.getFirst_name());
            userModel.setLast_name(user.getLast_name());
            userModel.setEmail(user.getEmail());
            userModel.setPhone_number(user.getPhone_number());
            userModel.setRole(user.getRole());
            userModel.setAddress(user.getAddress());
            userModel.setPicture(user.getPicture());
            userModel.setPassword(user.getPassword());
            userModel.setIn_Conger(user.getIsIn_Conger());
            userModel.setActive(user.isActive());
            if(user.getDepartement() != null){
                userModel.setDepartement_id(user.getDepartement().getId());
            }
            userModel.setEntreprise_id(user.getEntreprise().getId());

            return userModel;
        } else
            return null;
    }

    public UserModel findByEmail(String email) {
        Users user = userRepository.findByEmail(email).orElse(null);
        UserModel model = new UserModel();
        if (user != null) {
            model.setId(user.getId());
            model.setFirst_name(user.getFirst_name());
            model.setLast_name(user.getLast_name());
            model.setEmail(user.getEmail());
            model.setPhone_number(user.getPhone_number());
            model.setRole(user.getRole());
            model.setAddress(user.getAddress());
            model.setPicture(user.getPicture());
            model.setPassword(user.getPassword());
            model.setIn_Conger(user.getIsIn_Conger());
            model.setActive(user.isActive());
            if(user.getDepartement() != null){
                model.setDepartement_id(user.getDepartement().getId());
            }
            model.setEntreprise_id(user.getEntreprise().getId());
            return model;
        } else
            return null;

    }

    public List<UserModel> getAllUsers() {
        List<Users> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            Users user = users.get(i);
            UserModel userModel = new UserModel();

            userModel.setFirst_name(user.getFirst_name());
            userModel.setLast_name(user.getLast_name());
            userModel.setEmail(user.getEmail());
            userModel.setPhone_number(user.getPhone_number());
            userModel.setRole(user.getRole());
            userModel.setAddress(user.getAddress());
            userModel.setPicture(user.getPicture());
            userModel.setPassword(user.getPassword());
            userModel.setDepartement_id(user.getDepartement().getId());
            userModel.setIn_Conger(user.getIsIn_Conger());
            userModel.setActive(user.isActive());
            userModel.setEntreprise_id(user.getEntreprise().getId());
        }

        return userModels;
    }

    @Transactional
    public void updateUser(UserModel user) {
        Users currentUser = userRepository.findById(user.getId()).orElse(null);
        if (currentUser != null) {
            currentUser.setFirst_name(user.getFirst_name());
            currentUser.setLast_name(user.getLast_name());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone_number(user.getPhone_number());
            currentUser.setRole(user.getRole());
            currentUser.setAddress(user.getAddress());
            currentUser.setPicture(user.getPicture());
            currentUser.setPassword(user.getPassword());
            logger.info("Password : "+ user.getPassword());
            currentUser.setIn_Conger(user.getIsIn_Conger());
            currentUser.setActive(user.isActive());
            if(currentUser.getDepartement()!=null){
                currentUser.setDepartement(departementRepository.findById(user.getDepartement_id()).orElse(null));
            }
            currentUser.setEntreprise(entrepriseRepository.findById(user.getEntreprise_id()).orElse(null));
            userRepository.save(currentUser);
        }
    }

    public List<UserModel> getAllUserByDepartementId(int dep_id) {
        List<Users> users = userRepository.getAllUsersByDepartementId(dep_id);
        List<UserModel> userModels = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            Users user = users.get(i);
            UserModel userModel = new UserModel();
            userModel.setFirst_name(user.getFirst_name());
            userModel.setLast_name(user.getLast_name());
            userModel.setEmail(user.getEmail());
            userModel.setPhone_number(user.getPhone_number());
            userModel.setRole(user.getRole());
            userModel.setAddress(user.getAddress());
            userModel.setPicture(user.getPicture());
            userModel.setPassword(user.getPassword());
            userModel.setIn_Conger(user.getIsIn_Conger());
            userModel.setDepartement_id(user.getDepartement().getId());
            userModel.setEntreprise_id(user.getEntreprise().getId());
            userModel.setActive(user.isActive());
            userModels.add(userModel);
        }
        return userModels;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
