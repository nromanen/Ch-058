package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.IssueVote;
import com.shrralis.ssdemo1.repository.IssuesVotesRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class IssueVotesServiceImpl implements IIssueVotesService {
    @Autowired
    private IssuesVotesRepository issuesVotesRepository;

    @Override
    public Optional<IssueVote> getByVoterIdAndIssueId(Integer voterId, Integer issueId) {
        return issuesVotesRepository.getByVoterIdAndIssueId(voterId, issueId);
    }

    @Override
    public void deleteByVoterIdAndIssueId(Integer voterId, Integer issueID) {
        issuesVotesRepository.deleteAllByVoterIdAndIssueId(voterId, issueID);
    }

    @Override
    public void insertIssueVote(Integer issueId, Integer voterId, Boolean vote) {
        issuesVotesRepository.insertLikeOrDislike(issueId, voterId, vote);
    }

    @Override
    public Integer countByVoteAndIssue(Boolean vote, Integer issueId) {
        return issuesVotesRepository.countByVoteAndIssue(vote, issueId);
    }
}
