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

import com.shrralis.ssdemo1.entity.IssueVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssuesVotesRepository extends JpaRepository<IssueVote, IssueVote.Id> {
    @Query(value = "SELECT * FROM issues_votes WHERE voter_id = ?1 AND issue_id=?2", nativeQuery = true)
    Optional<IssueVote> getByVoterIdAndIssueId(Integer voterId, Integer issueId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM issues_votes WHERE voter_id=?1 AND issue_id=?2", nativeQuery = true)
    void deleteAllByVoterIdAndIssueId(Integer voterId, Integer issueId);

    @Modifying
    @Query(value = "INSERT INTO issues_votes(issue_id, voter_id, vote) VALUES(?1, ?2, ?3)", nativeQuery = true)
    void insertLikeOrDislike(Integer issueId, Integer voterId, Boolean vote);

    @Query(value = "SELECT COUNT(vote) FROM issues_votes WHERE vote=?1 AND issue_id=?2", nativeQuery = true)
    Integer countByVoteAndIssue(Boolean vote, Integer issueId);
}
