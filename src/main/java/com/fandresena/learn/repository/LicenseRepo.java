package com.fandresena.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fandresena.learn.entity.License;

public interface LicenseRepo  extends JpaRepository<License,Integer>{

}
