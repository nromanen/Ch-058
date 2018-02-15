package com.softserveinc.geocitizen.controller.api;

import com.softserveinc.geocitizen.entity.FullMessage;
import com.softserveinc.geocitizen.entity.Message;
import com.softserveinc.geocitizen.entity.Notification;
import com.softserveinc.geocitizen.exception.AccessDeniedException;
import com.softserveinc.geocitizen.service.interfaces.IMessageService;
import com.softserveinc.geocitizen.service.interfaces.INotificationService;
import com.softserveinc.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

	private final INotificationService notificationService;
	private final IMessageService messageService;

	@Autowired
	public ChatController(INotificationService notificationService, IMessageService messageService) {
		this.notificationService = notificationService;
		this.messageService = messageService;
	}

	//	@Secured(ADMIN_ROLE)
	@RequestMapping("/api/{issueId}/{userId}/{adminId}/chat")
	public JsonResponse checkAccess(@PathVariable("issueId") Long issueId,
	                                @PathVariable("userId") Long userId,
	                                @PathVariable("adminId") Long adminId) throws AccessDeniedException {
		return new JsonResponse(messageService.checkAccessForAdmin(issueId, userId, adminId));
	}

	//	@Secured({USER_ROLE, ADMIN_ROLE})
	@RequestMapping("/api/{issueId}/{userId}/chat")
	public JsonResponse checkChatExist(@PathVariable("issueId") Long issueId, @PathVariable("userId") Long userId)
			throws AccessDeniedException {
		return new JsonResponse(messageService.checkChat(issueId, userId));
	}

	//	@Secured({USER_ROLE, ADMIN_ROLE})
	@RequestMapping("/api/message/all/{issueId}/{userId}")
	public JsonResponse getMessages(@PathVariable("issueId") Long issueId,
	                                @PathVariable("userId") Long userId) throws AccessDeniedException {
		return new JsonResponse(messageService.getAllMessagesForChat(issueId, userId));
	}

	//	@Secured(ADMIN_ROLE)
	@RequestMapping("/api/chat/room/all/{adminId}")
	public JsonResponse getChatRooms(@PathVariable("adminId") Long adminId) {
		return new JsonResponse(messageService.getAllChatRooms(adminId));
	}

	//	@Secured(ADMIN_ROLE)
	@RequestMapping("/api/notification/all")
	public JsonResponse getNotifications() {
		return new JsonResponse(notificationService.getAllNotifications());
	}

	@MessageMapping("/api/message/{issueId}/{userId}")
	@SendTo("/api/topic/broadcast/{issueId}/{userId}")
	public JsonResponse messaging(Message input,
	                              @DestinationVariable Long userId,
	                              @DestinationVariable Long issueId) {
		messageService.saveMessage(FullMessage.messageBuilder(input, userId, issueId));
		return new JsonResponse(input);
	}

	@MessageMapping("/api/connect/wait")
	@SendTo("/api/checkTopic/broadcast")
	public JsonResponse notificationWait(Notification notification) {
		notificationService.setWaiting(notification);
		return new JsonResponse(notification);
	}

	@MessageMapping({"/api/connect/cancelNotification", "/api/connect/accept", "/api/connect/delete"})
	@SendTo("/api/checkTopic/broadcast")
	public JsonResponse notificationDelete(Notification notification) {
		notificationService.removeNotification(notification);
		return new JsonResponse(notification);
	}

	@MessageMapping("/api/connect/alert")
	@SendTo("/api/checkTopic/broadcast")
	public JsonResponse notificationAdd(Notification notification) {
		notificationService.addNotification(notification);
		return new JsonResponse(notification);
	}
}