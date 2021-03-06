package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.FullMessage;
import com.shrralis.ssdemo1.exception.AccessDeniedException;

import java.util.List;

public interface IMessageService {

    FullMessage saveMessage(FullMessage message);
    List<FullMessage> getAllMessages() throws AccessDeniedException;
    FullMessage getMessage(Long id) throws AccessDeniedException;
    boolean checkChat(Long issueId, Long userId) throws AccessDeniedException;
    List<FullMessage> getAllMessagesForChat(Long issueId, Long userId) throws AccessDeniedException;
}
