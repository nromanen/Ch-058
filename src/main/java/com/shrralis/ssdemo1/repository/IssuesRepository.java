/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuesRepository extends JpaRepository<Issue, Integer> {

	Optional<Issue> findById(int id);

	List<Issue> findByMapMarker_Id(int mapMarkerId);

	@Query(value = "SELECT type_id FROM issues WHERE map_marker_id = ?1", nativeQuery = true)
	int[] getIssueTypeById(int id);

	Page<Issue> findByTitleContainingOrTextContainingAllIgnoreCase(String title, String text, Pageable pageable);

	Page<Issue> findByAuthor_Id(Integer id, Pageable pageable);

	Integer deleteById(Integer id);

	@Modifying
	@Query("UPDATE Issue i SET i.closed = ?1 WHERE i.id = ?2")
	Integer setStatus(Boolean closed, Integer id);

	Page<Issue> findByClosedTrue(Pageable pageable);

	Page<Issue> findByClosedFalse(Pageable pageable);

	Page<Issue> findAll(Pageable pageable);
}
