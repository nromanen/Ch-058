package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.IssueVote;
import com.shrralis.ssdemo1.repository.IssuesVotesRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class IssueVotesServiceImpl implements IIssueVotesService {
	@Autowired
	private IssuesVotesRepository issuesVotesRepository;

	@Override
	public IssueVote getByVoterIdAndIssueId(int voterId, int issueId) {
		return issuesVotesRepository.findByVoter_IdAndIssue_Id(voterId, issueId).orElseThrow(NullPointerException::new);
	}

	@Override
	public void deleteByVoterIdAndIssueId(int voterId, int issueID) {
		issuesVotesRepository.deleteAllByVoter_IdAndIssue_Id(voterId, issueID);
	}

	@Override
	public void insertIssueVote(int issueId, int voterId, boolean vote) {
		issuesVotesRepository.insertLikeOrDislike(issueId, voterId, vote);
	}

	@Override
	public long countByVoteAndIssue(boolean vote, int issueId) {
		return issuesVotesRepository.countByVoteAndIssue_Id(vote, issueId);
	}
}
