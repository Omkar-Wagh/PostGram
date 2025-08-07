package com.demo.service;

import com.demo.model.User;
import com.demo.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Notification;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;

    public List<Notification> getNotificationsByUser(User currentUser) {
        return notificationRepo.findNotificationByUser(currentUser);
    }
    public List<Notification> getUnseenNotificationsByUser(User user) {
        return notificationRepo.findByUserAndIsSeenFalse(user);
    }

    public void createNotification(Notification notification) {
        notificationRepo.save(notification);
    }

    public void saveAll(List<Notification> notifications) {
        notificationRepo.saveAll(notifications);
    }
}
