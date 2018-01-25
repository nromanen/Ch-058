package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.FullMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface MessageRepository extends JpaRepository<FullMessage, Long> {

	boolean existsByIssueIdAndUserId(Long issueId, Long userId);
	List<FullMessage> findAllByIssueIdAndUserId(Long issueId, Long userId);
	@Query(value = "SELECT * FROM (SELECT DISTINCT ON (issueid, userid) * FROM message WHERE authorid = ?1) AS chatrooms ORDER BY date DESC", nativeQuery = true)
	List<FullMessage> findAllChatRooms(Long adminId);
	boolean existsByIssueIdAndUserIdAndAuthorId(Long issueId, Long userId, Long authorId);
}
