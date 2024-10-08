package com.fandresena.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.fandresena.learn.entity.Absence;

public interface AbsenceRepo extends JpaRepository<Absence, Integer> {
    @Query("SELECT a FROM Absence a INNER JOIN a.user u WHERE u.departement.id = :id")
    List<Absence> getAllAbsencesByDepId(int id);

    @Query("SELECT a FROM Absence a INNER JOIN a.user u WHERE u.id = :id")
    List<Absence> getAllAbsencesByUserId(int id);
}
