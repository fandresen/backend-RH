package com.fandresena.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fandresena.learn.entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification,Long>{

    @Query("SELECT n FROM Notification n WHERE n.receipientId.id = :userId")
    List<Notification> findAllByReceipient_Id(int userId);

}
