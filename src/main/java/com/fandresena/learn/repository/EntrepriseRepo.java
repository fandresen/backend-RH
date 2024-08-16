package com.fandresena.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fandresena.learn.entity.Entreprise;

public interface EntrepriseRepo extends JpaRepository<Entreprise,Integer> {

}
