package com.fandresena.learn.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fandresena.learn.entity.NewPasswordToken;

public interface NewPasswordTokenRepo extends JpaRepository<NewPasswordToken,Integer>{
    @Modifying
    @Query("DELETE FROM NewPasswordToken token WHERE token.expired_date < :now")
    void deleteAllByExpireDateBefore(@Param("now") LocalDateTime now);

    NewPasswordToken findByToken(String token);
    
    @Query("SELECT token FROM NewPasswordToken token INNER JOIN token.user u WHERE u.id = :user_id")
    NewPasswordToken findByUser_id(@Param("user_id")int user_id);
}
