package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.fandresena.learn.entity.User;
import com.fandresena.learn.model.UserModel;
import com.fandresena.learn.repository.DepartementRepo;
import com.fandresena.learn.repository.UserRepo;

public class UserDAO {
    @Autowired
    private UserRepo userRepository;

    public void createUser(UserModel userModel){
        User user = userRepository.findById(userModel.getId()).orElse(null);
        userRepository.save(user);
    }

    public UserModel getUserById(int id){
        User user = userRepository.findById(id).orElse(null);
        if(user!= null){
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
            
            return userModel;
        }
        else return null;
    }

    public List<UserModel> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            User user = users.get(i);
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
            userModel.setEntreprise_id(user.getEntreprise().getId());
        }

        return userModels;
    }

    public User updateUser(int id, User user){
        User currentUser = userRepository.findById(id).orElse(null);
        if(currentUser != null){
            currentUser.setFirst_name(user.getFirst_name());
            currentUser.setLast_name(user.getLast_name());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone_number(user.getPhone_number());
            currentUser.setRole(user.getRole());
            currentUser.setAddress(user.getAddress());
            currentUser.setPicture(user.getPicture());
            currentUser.setPassword(user.getPassword());
            currentUser.setIn_Conger(user.getIsIn_Conger());
            currentUser.setDepartement(user.getDepartement());
            currentUser.setEntreprise(user.getEntreprise());
            return userRepository.save(currentUser);
        }
        return null;
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
}
