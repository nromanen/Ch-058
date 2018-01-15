package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.repository.MessageRepository;
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
    public List<FullMessage> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public FullMessage getMessage(Long id) {
        return messageRepository.findOne(id);
    }

    @Override
    public boolean checkChat(Long issueId, Long userId) {
        return messageRepository.existsByIssueIdAndUserId(issueId, userId);
    }

    @Override
    public List<FullMessage> getAllMessagesForChat(Long issueId, Long userId) {
        return messageRepository.findAllByIssueIdAndUserId(issueId, userId);
    }
}
