package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.ChatRoom;
import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AccessDeniedException;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.MessageRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

	private final MessageRepository messageRepository;
	private final IssuesRepository issuesRepository;
	private final UsersRepository usersRepository;

	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository,
							  IssuesRepository issuesRepository,
							  UsersRepository usersRepository) {
		this.messageRepository = messageRepository;
		this.issuesRepository = issuesRepository;
		this.usersRepository = usersRepository;
	}

	@Override
	public FullMessage saveMessage(FullMessage message) {
		return messageRepository.save(message);
	}

	@Override
	public List<FullMessage> getAllMessages() throws AccessDeniedException {
		if (AuthorizedUser.getCurrent().getType().equals(User.Type.ROLE_ADMIN)) {
			return messageRepository.findAll();
		}
		throw new AccessDeniedException();
	}

	@Override
	public FullMessage getMessage(Long id) throws AccessDeniedException {
		if (AuthorizedUser.getCurrent().getType().equals(User.Type.ROLE_ADMIN)) {
			return messageRepository.findOne(id);
		}
		throw new AccessDeniedException();
	}

	@Override
	public boolean checkChat(Long issueId, Long userId) throws AccessDeniedException {
		if( new Long(AuthorizedUser.getCurrent().getId()).equals(userId) ||
				AuthorizedUser.getCurrent().getType().equals(User.Type.ROLE_ADMIN)) {
			return messageRepository.existsByIssueIdAndUserId(issueId, userId);
		}
		throw new AccessDeniedException();
	}

	@Override
	public List<FullMessage> getAllMessagesForChat(Long issueId, Long userId) throws AccessDeniedException {
		if( new Long(AuthorizedUser.getCurrent().getId()).equals(userId) ||
				AuthorizedUser.getCurrent().getType().equals(User.Type.ROLE_ADMIN)) {
			return messageRepository.findAllByIssueIdAndUserId(issueId, userId);
		}
		throw new AccessDeniedException();
	}

	@Override
	public List<ChatRoom> getAllChatRooms(Long adminId){
		List<FullMessage> chatRoomMessages = messageRepository.findAllChatRooms(adminId);
		List<ChatRoom> chatRooms = new ArrayList<>();
		for(FullMessage chatRoom : chatRoomMessages){
			int userId = Integer.valueOf(chatRoom.getUserId().toString());
			int issueId = Integer.valueOf(chatRoom.getIssueId().toString());
			String login = usersRepository.findById(userId).get().getLogin();
			String issueTitle = issuesRepository.findById(issueId).get().getTitle();
			chatRooms.add(new ChatRoom(login, issueTitle, userId, issueId));
		}
		return chatRooms;
	}
}
