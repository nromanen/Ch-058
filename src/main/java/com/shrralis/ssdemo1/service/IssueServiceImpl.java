package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class IssueServiceImpl implements IIssueService {

    @Autowired
    private IssuesRepository issuesRepository;

    @Override
    public Optional<Issue> getById(Integer id) {
        return issuesRepository.findById(id);
    }
}
