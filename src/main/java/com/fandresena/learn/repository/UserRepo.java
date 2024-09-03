package com.fandresena.learn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fandresena.learn.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u INNER JOIN u.departement d WHERE d.id = :id")
    List<Users> getAllUsersByDepartementId(int id);

    Optional<Users> findByEmail(String email);
}
