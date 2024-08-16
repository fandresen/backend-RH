package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fandresena.learn.entity.Superuser;
import com.fandresena.learn.model.SuperUserModel;
import com.fandresena.learn.repository.SuperUserRepo;

public class SuperUserDAO {
    @Autowired
    private SuperUserRepo superUserRepo;

    public void createSuperUser(SuperUserModel superuserModel){
        Superuser superuser = new Superuser();
        superuser.setEmail(superuserModel.getEmail());
        superuser.setPassword(superuserModel.getPassword());
        superUserRepo.save(superuser);
    }

    public SuperUserModel getSuperUserById(int id){
        
        Superuser superuser = superUserRepo.findById(id).orElse(null);
        if(superuser!= null){
            SuperUserModel superUserModel = new SuperUserModel();
            superUserModel.setId(superuser.getId());
            superUserModel.setEmail(superuser.getEmail());
            superUserModel.setPassword(superuser.getPassword());
            return superUserModel;
        }
        else return null;
    }

    public List<SuperUserModel> getAllSuperuser(){
    List<Superuser> superUsers = superUserRepo.findAll();
    List<SuperUserModel> superUserModels = new ArrayList<>();
    for(int i=0; i<superUsers.size(); i++){
        Superuser superUser = superUsers.get(i);
        SuperUserModel superUserModel = new SuperUserModel();
        superUserModel.setId(superUser.getId());
        superUserModel.setEmail(superUser.getEmail());
        superUserModel.setPassword(superUser.getPassword());
        superUserModels.add(superUserModel);
    }
    return superUserModels;
    }

    public void updateSuperUser(int id, SuperUserModel superuser){
        Superuser currentSuperuser = superUserRepo.findById(id).orElse(null);
        if(currentSuperuser != null){
            currentSuperuser.setEmail(superuser.getEmail());
            currentSuperuser.setPassword(superuser.getPassword());
            
            superUserRepo.save(currentSuperuser);
        }
    }

    public void deleteSuperUser(int id){
        superUserRepo.deleteById(id);
    }

}