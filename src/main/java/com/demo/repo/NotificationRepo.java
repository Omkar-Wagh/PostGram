package com.demo.repo;

import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Notification;
import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> findNotificationByUser(User currentUser);
    List<Notification> findByUserAndIsSeenFalse(User user);
}
