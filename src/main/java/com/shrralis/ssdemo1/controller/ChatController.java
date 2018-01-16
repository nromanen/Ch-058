package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.entity.Message;
import com.shrralis.ssdemo1.entity.Notification;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AccessDeniedException;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.IssueServiceImpl;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.ssdemo1.service.interfaces.IMessageService;
import com.shrralis.ssdemo1.service.interfaces.INotificationService;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    private final INotificationService notificationService;
    private final IMessageService messageService;

    @Autowired
    public ChatController(INotificationService notificationService,
                          IMessageService messageService){
        this.notificationService = notificationService;
        this.messageService = messageService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/{issueId}/{userId}/chat")
    public JsonResponse checkChatExist(@PathVariable("issueId") Long issueId, @PathVariable("userId") Long userId)
            throws AccessDeniedException {
        return new JsonResponse(messageService.checkChat(issueId, userId));
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/message/all/{issueId}/{userId}")
    public JsonResponse getMessages(@PathVariable("issueId") Long issueId,
                                         @PathVariable("userId") Long userId) throws AccessDeniedException {
        return new JsonResponse(messageService.getAllMessagesForChat(issueId, userId));
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/notification/all")
    public JsonResponse getNotifications(){
        return new JsonResponse(notificationService.getAllNotifications());
    }

    @MessageMapping("/message/{issueId}/{userId}")
    @SendTo("/topic/broadcast/{issueId}/{userId}")
    public JsonResponse messaging(Message input,
                             @DestinationVariable Long userId,
                             @DestinationVariable Long issueId){

        messageService.saveMessage(
                FullMessage.messageBuilder(input, userId, issueId)
        );
        return new JsonResponse(input);
    }

    @MessageMapping("/connect")
    @SendTo("/checkTopic/broadcast")
    public JsonResponse notificateAdmins(Notification notification) {
        if(notification.getText().equals("Alert")) {
            notificationService.addNotification(notification);
        }
        if(notification.getText().equals("Accept")) {
            notificationService.removeNotification(notification);
        }
        if(notification.getText().equals("Notification timed out")) {
            notificationService.setWaiting(notification);
        }
        if(notification.getText().equals("Cancel notification")){
            notificationService.removeNotification(notification);
        }
        if(notification.getText().equals("Delete")){
            notificationService.removeNotification(notification);
        }
        return new JsonResponse(notification);
    }
}