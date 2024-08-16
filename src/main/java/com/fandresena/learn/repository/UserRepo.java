package com.fandresena.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fandresena.learn.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u INNER JOIN u.departement d WHERE d.id = :id")
    List<User> getAllUsersByDepartementId(int id);
}
