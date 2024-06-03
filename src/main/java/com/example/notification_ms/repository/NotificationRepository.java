package com.example.notification_ms.repository;


import com.example.notification_ms.entity.Notification;
import com.example.notification_ms.entity.dto.EmailRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByFromEmail(String email);
}
