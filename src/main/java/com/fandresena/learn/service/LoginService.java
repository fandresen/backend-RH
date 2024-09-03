package com.fandresena.learn.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.UserModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LoginService implements UserDetailsService{
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserModel newUser = userDAO.findByEmail(email);
        if(newUser == null) throw new RuntimeException("User not found");
        
        // Additional logic to build UserDetails object
        return new org.springframework.security.core.userdetails.User(
            newUser.getUsername(),
            newUser.getPassword(),
            newUser.getAuthorities()
        );
    }
}
