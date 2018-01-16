package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AccessDeniedException;
import com.shrralis.ssdemo1.repository.MessageRepository;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

    @Resource
    MessageRepository messageRepository;

    @Override
    public FullMessage saveMessage(FullMessage message) {
        return messageRepository.save(message);
    }

    @Override
    public List<FullMessage> getAllMessages() throws AccessDeniedException {
        if(AuthorizedUser.getCurrent().getType().equals(User.Type.ADMIN)) {
            return messageRepository.findAll();
        }
        else{
            throw new AccessDeniedException();
        }
    }

    @Override
    public FullMessage getMessage(Long id) throws AccessDeniedException {
        if(AuthorizedUser.getCurrent().getType().equals(User.Type.ADMIN)) {
            return messageRepository.findOne(id);
        }
        else{
            throw new AccessDeniedException();
        }
    }

    @Override
    public boolean checkChat(Long issueId, Long userId) throws AccessDeniedException {
        if( new Long(AuthorizedUser.getCurrent().getId()).equals(userId) ||
                AuthorizedUser.getCurrent().getType().equals(User.Type.ADMIN) ) {
            return messageRepository.existsByIssueIdAndUserId(issueId, userId);
        }
        else{
            throw new AccessDeniedException();
        }
    }

    @Override
    public List<FullMessage> getAllMessagesForChat(Long issueId, Long userId) throws AccessDeniedException {
        if( new Long(AuthorizedUser.getCurrent().getId()).equals(userId) ||
                AuthorizedUser.getCurrent().getType().equals(User.Type.ADMIN) ) {
            return messageRepository.findAllByIssueIdAndUserId(issueId, userId);
        }
        else{
            throw new AccessDeniedException();
        }
    }
}
