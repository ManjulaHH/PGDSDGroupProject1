package org.upgrad.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Notification;

import java.util.List;
public interface NotificationService {
    List<Notification> getNewNotifications(int id);
    List<Notification> getAllNotifications(int id);
    void markReadNewNotifications(List<Notification> notificationList);
}
