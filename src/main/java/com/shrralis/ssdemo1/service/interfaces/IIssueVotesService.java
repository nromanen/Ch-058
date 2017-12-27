package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.IssueVote;

import java.util.Optional;

public interface IIssueVotesService {
    Optional<IssueVote> getByVoterIdAndIssueId(Integer voterId, Integer issueId);

    void deleteByVoterIdAndIssueId(Integer voterId, Integer issueID);

    void insertIssueVote(Integer issueId, Integer voterId, Boolean vote);

    Integer countByVoteAndIssue(Boolean vote, Integer issueId);
}
