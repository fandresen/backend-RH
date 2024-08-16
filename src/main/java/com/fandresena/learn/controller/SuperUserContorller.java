package com.fandresena.learn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fandresena.learn.model.SuperUserModel;
import com.fandresena.learn.service.SuperUserService;

@RestController
@RequestMapping("/superuser")
public class SuperUserContorller {

    private final SuperUserService superUserService;

    @Autowired
    public SuperUserContorller(SuperUserService superUserService) {
        this.superUserService = superUserService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SuperUserModel>> getAllSuperUsers(){
        List<SuperUserModel> superUsers = superUserService.getAllSuperUsers();
        return ResponseEntity.ok(superUsers);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createSuperUser(@RequestBody SuperUserModel superUserModel){
        try {
            superUserService.createSuperUser(superUserModel);
            return ResponseEntity.ok("Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
