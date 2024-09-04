package com.fandresena.learn.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.SuperUserDAO;
import com.fandresena.learn.model.SuperUserModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginSuperUserService  implements UserDetailsService{
    SuperUserDAO superUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SuperUserModel superUser = superUserDAO.findByEmail(username);
        if(superUser == null) throw new RuntimeException("Super user not found");
        return superUser;
    }   
}
