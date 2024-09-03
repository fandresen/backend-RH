package com.fandresena.learn.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fandresena.learn.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expirDate < :now")
    void deleteAllByExpireDateBefore(@Param("now") Date now);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.token = :refreshToken")
    void deleteByToken(String refreshToken);

}
