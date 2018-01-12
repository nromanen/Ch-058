package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.Notification;

import java.util.List;

public interface INotificationService {

    Notification addNotification(Notification notification);
    Long removeNotification(Notification notification);
    List<Notification> getAllNotifications();
    Notification setWaiting(Notification notification);
}
