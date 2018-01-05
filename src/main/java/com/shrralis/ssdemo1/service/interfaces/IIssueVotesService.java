package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.IssueVote;

import java.util.Optional;

public interface IIssueVotesService {

	Optional<IssueVote> getByVoterIdAndIssueId(int voterId, int issueId);

	void deleteByVoterIdAndIssueId(int voterId, int issueID);

	void insertIssueVote(int issueId, int voterId, boolean vote);

	Integer countByVoteAndIssue(boolean vote, int issueId);
}
