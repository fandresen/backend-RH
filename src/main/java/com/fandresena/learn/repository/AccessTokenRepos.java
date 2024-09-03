package com.fandresena.learn.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fandresena.learn.entity.AccessToken;

public interface AccessTokenRepos extends JpaRepository<AccessToken,Long>{
     AccessToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM AccessToken at WHERE at.expirDate < :now")
    void deleteAllByExpireDateBefore(@Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM  AccessToken at WHERE at.token = :accessToken")
    void deleteByToken(@Param("accessToken") String accessToken);
}
