package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Notification;
import org.upgrad.repository.NotificationRepository;

import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepo;



    public NotificationServiceImpl(){

    }

    @Override
    public List<Notification> getNewNotifications(int id) {

        return notificationRepo.getNewNotificationByUser(id);
    }

    @Override
    public List<Notification> getAllNotifications(int id) {

        return notificationRepo.getAllNotificationByUser(id);

    }

    @Override
    public void markReadNewNotifications(List<Notification> notificationList) {
        for(Notification notification: notificationList){
            notificationRepo.markReadNewNotificationByUser(notification.getId());
        }

    }
}
