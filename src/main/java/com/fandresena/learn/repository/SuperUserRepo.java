package com.fandresena.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fandresena.learn.entity.Superuser;

public interface SuperUserRepo extends JpaRepository<Superuser, Integer>{

}
