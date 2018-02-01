package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

	boolean existsByIssueIdAndUserId(Long issueId, Long userId);

	Long deleteByIssueIdAndUserId(Long issueId, Long userId);

	Notification findByIssueIdAndUserId(Long issueId, Long userId);
}
