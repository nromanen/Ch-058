package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.entity.Message;
import com.shrralis.ssdemo1.entity.Notification;
import com.shrralis.ssdemo1.service.interfaces.IMessageService;
import com.shrralis.ssdemo1.service.interfaces.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    INotificationService notificationService;

    @Autowired
    IMessageService messageService;

    private static final Logger logger =
            LoggerFactory.getLogger(ChatController.class);

    @RequestMapping("/{issueId}/{userId}/chat")
    public Boolean checkChatExist(@PathVariable("issueId") Long issueId, @PathVariable("userId") Long userId){
        return messageService.checkChat(issueId, userId);
    }

    @RequestMapping("/message/all/{issueId}/{userId}")
    public List<FullMessage> getMessages(@PathVariable("issueId") Long issueId,
                                         @PathVariable("userId") Long userId){
        return messageService.getAllMessagesForChat(issueId, userId);
    }

    @RequestMapping("/notification/all")
    public List<Notification> getNotifications(){
        return notificationService.getAllNotifications();
    }

    @MessageMapping("/message/{issueId}/{userId}")
    @SendTo("/topic/broadcast/{issueId}/{userId}")
    public Message messaging(Message input, @DestinationVariable Long userId,
                         @DestinationVariable Long issueId) {
        FullMessage fullMessage = new FullMessage();
        fullMessage.setText(input.getText());
        fullMessage.setIssueId(issueId);
        fullMessage.setUserId(userId);
        fullMessage.setAuthorId(input.getAuthorId());
        fullMessage.setDate(LocalDateTime.now());
        messageService.saveMessage(fullMessage);
        return input;
    }

    @MessageMapping("/connect")
    @SendTo("/checkTopic/broadcast")
    public Notification notificateAdmins(Notification notification){
        if(notification.getText().equals("Alert"))
            notificationService.addNotification(notification);
        else if(notification.getText().equals("Accept"))
            notificationService.removeNotification(notification);
        return notification;
    }
}