package com.softserveinc.geocitizen.repository;

import com.softserveinc.geocitizen.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	boolean existsByIssueIdAndUserId(Long issueId, Long userId);

	Long deleteByIssueIdAndUserId(Long issueId, Long userId);

	Notification findByIssueIdAndUserId(Long issueId, Long userId);
}
