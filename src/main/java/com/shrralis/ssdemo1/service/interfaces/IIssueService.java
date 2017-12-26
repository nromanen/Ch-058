package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.Issue;

import java.util.Optional;

public interface IIssueService {
    Optional<Issue> getById(Integer id);
}
