package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.IssueVote;

public interface IIssueVotesService {

	IssueVote getByVoterIdAndIssueId(int voterId, int issueId);

	void deleteByVoterIdAndIssueId(int voterId, int issueID);

	void insertIssueVote(int issueId, int voterId, boolean vote);

	long countByVoteAndIssue(boolean vote, int issueId);
}
