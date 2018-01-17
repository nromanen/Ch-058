package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.Notification;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AccessDeniedException;
import com.shrralis.ssdemo1.repository.NotificationRepository;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.INotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements INotificationService{

    @Resource
    NotificationRepository notificationRepository;

    @Override
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Long removeNotification(Notification notification){
        return notificationRepository.deleteByIssueIdAndUserId(notification.getIssueId(), notification.getUserId());
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification setWaiting(Notification notification) {

        Notification getedNot = notificationRepository.findByIssueIdAndUserId(notification.getIssueId(),
                notification.getUserId());

        getedNot.setWaiting(notification.getWaiting());

        return notificationRepository.save(getedNot);
    }
}
