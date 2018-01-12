package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.FullMessage;

import java.util.List;

public interface IMessageService {

    FullMessage saveMessage(FullMessage message);
    List<FullMessage> getAllMessages();
    FullMessage getMessage(Long id);
    boolean checkChat(Long issueId, Long userId);
    List<FullMessage> getAllMessagesForChat(Long issueId, Long userId);
}
