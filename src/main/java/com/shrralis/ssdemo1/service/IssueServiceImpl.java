package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
import com.shrralis.ssdemo1.repository.IssueTypesRepository;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.shrralis.ssdemo1.security.model.AuthorizedUser.getCurrent;

@Service
@Transactional
public class IssueServiceImpl implements IIssueService {
	private static final String OPENED_TYPE = "PROBLEM";

	private final IssuesRepository issuesRepository;
	private final MapMarkersRepository mapMarkersRepository;
	private final UsersRepository usersRepository;
	private final IssueTypesRepository issueTypesRepository;

	@Autowired
	public IssueServiceImpl(IssuesRepository issuesRepository,
							MapMarkersRepository mapMarkersRepository,
							UsersRepository usersRepository,
							IssueTypesRepository issueTypesRepository) {
		this.issuesRepository = issuesRepository;
		this.mapMarkersRepository = mapMarkersRepository;
		this.usersRepository = usersRepository;
		this.issueTypesRepository = issueTypesRepository;
	}

	@Override
	public Issue getById(Integer id) {
		return issuesRepository.findById(id).orElseThrow(NullPointerException::new);
	}

	@Override
	public Issue saveIssue(MapDataDTO dto, Image image) {
		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());
		User user = usersRepository.findOne(getCurrent().getId());
		boolean closed = !dto.getTypeName().equals(OPENED_TYPE);
		Issue.Type type = getTypeByName(dto.getTypeName());

		return issuesRepository.save(Issue.Builder.anIssue()
				.setMapMarker(marker)
				.setTitle(dto.getTitle())
				.setText(dto.getDesc())
				.setAuthor(user)
				.setImage(image)
				.setType(type)
				.setClosed(closed)
				.setCreatedAt(LocalDateTime.now())
				.setUpdatedAt(LocalDateTime.now())
				.build());
	}

	@Override
	public List<Issue> getAllIssueByMapMarker(int mapMarkerId) {
		return issuesRepository.findByMapMarker_Id(mapMarkerId);
	}

	@Override
	public Issue findById(Integer id) throws AbstractCitizenException {
		return issuesRepository.findById(id).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.ISSUE));
	}

	@Override
	public Page<Issue> findByTitleOrText(String title, String text, Pageable pageable) {
		return issuesRepository.findByTitleContainingOrTextContainingAllIgnoreCase(title, text, pageable);
	}

	@Override
	public Page<Issue> findAuthorId(Integer id, Pageable pageable) {
		return issuesRepository.findByAuthor_Id(id, pageable);
	}

	@Override
	public Page<Issue> findAll(Pageable pageable) {
		return issuesRepository.findAll(pageable);
	}

	@Override
	public Integer deleteById(Integer id) throws AbstractCitizenException {
		Issue issue = issuesRepository.findById(id)
				.orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.ISSUE));
		issuesRepository.delete(issue);
		if (issuesRepository.countAllByMapMarker(issue.getMapMarker()) <= 1) {
			mapMarkersRepository.delete(issue.getMapMarker());
		}
		return 0;
	}

	@Override
	public Integer setStatus(Boolean flag, Integer id) throws AbstractCitizenException {
		Issue issue = issuesRepository.findById(id).
				orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.ISSUE));

		System.out.println("ID: " + issue.getId());
		System.out.println("TYPE: " + issue.getType().getName());

		if (StringUtils.equalsIgnoreCase(issue.getType().getName(), "PROBLEM")) {
			issuesRepository.setStatus(flag, id);
		}
		return 0;
//		if (issuesRepository.findOne(id) == null) {
//			throw new EntityNotExistException(EntityNotExistException.Entity.ISSUE);
//		} else if ()
//
//		return issuesRepository.setStatus(flag, id);
	}

	public Page<Issue> findClosedTrue(Pageable pageable) {
		return issuesRepository.findByClosedTrue(pageable);
	}

	public Page<Issue> findClosedFalse(Pageable pageable) {
		return issuesRepository.findByClosedFalse(pageable);
	}

	private Issue.Type getTypeByName(String type) {

		Issue.Type issueType = issueTypesRepository.getByName(type);

		if (issueType == null) {
			issueType = new Issue.Type();
			issueType.setName(type);
		}
		return issueType;

	}
}
