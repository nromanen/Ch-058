package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.repository.IssueTypesRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/30/18 at 1:05 PM
 */
@Service
@Transactional
public class IssueTypesService implements IIssueTypesService {

	private final IssueTypesRepository repository;

	@Autowired
	public IssueTypesService(final IssueTypesRepository repository) {
		this.repository = repository;
	}

	@Override
	@ReadOnlyProperty
	public List<Issue.Type> getAll() {
		return repository.findAll();
	}
}
