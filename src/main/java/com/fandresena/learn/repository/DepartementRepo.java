package com.fandresena.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fandresena.learn.entity.Departement;

public interface DepartementRepo extends JpaRepository<Departement , Integer>{
    @Query("SELECT d FROM Departement d INNER JOIN d.entreprise e WHERE e.id = :entrepriseId")
    List<Departement> getAllDepartmentsByEntrepriseId( @Param("entrepriseId") int entrepriseId);

}
