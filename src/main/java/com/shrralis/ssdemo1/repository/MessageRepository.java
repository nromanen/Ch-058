package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.FullMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<FullMessage, Long> {

    boolean existsByIssueIdAndUserId(Long issueId, Long userId);

    List<FullMessage> findAllByIssueIdAndUserId(Long issueId, Long userId);
}
