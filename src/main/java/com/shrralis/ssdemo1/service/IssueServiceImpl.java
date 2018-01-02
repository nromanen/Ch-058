package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.shrralis.ssdemo1.security.AuthorizedUser.getCurrent;


@Service
@Transactional
public class IssueServiceImpl implements IIssueService {

    private final IssuesRepository issuesRepository;
    private final MapMarkersRepository mapMarkersRepository;
    private final UsersRepository usersRepository;

	@Autowired
	public IssueServiceImpl(IssuesRepository issuesRepository,
	                        MapMarkersRepository mapMarkersRepository,
	                        UsersRepository usersRepository) {
		this.issuesRepository = issuesRepository;
		this.mapMarkersRepository = mapMarkersRepository;
		this.usersRepository = usersRepository;
	}

    @Override
    public Optional<Issue> getById(Integer id) {
        return issuesRepository.findById(id);
    }

    @Override
    public Issue createIssue(MapDataDTO data) {

		MapMarker marker = mapMarkersRepository.findById(data.getMarkerId()).orElse(null);

		// test author
	    User author = new User();
	    author.setLogin("Login");
	    author.setEmail("author@gmail.com");
	    author.setPassword("Password");
	    author.setName("Name");
	    author.setSurname("Surname");

	    // test Image
	    Image image = new Image();
	    image.setSrc("/path/ta/image");
	    image.setHash("hashhbshhashhashnashhxshhashhash");

	    Issue issue = new Issue();
        issue.setMapMarker(marker);


	    User user = usersRepository.findById(getCurrent().getId()).orElse(null);

        issue.setAuthor(user);
        //issue.setAuthorId(getCurrent().getId());
        issue.setTitle(data.getTitle());
        issue.setText(data.getText());
        issue.setImage(image);
        //issue.setImage(data.getImage());
        issue.setTypeId(data.getTypeId());
        boolean isClosed = data.getTypeId() != 1;
        issue.setClosed(isClosed);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        return issuesRepository.save(issue);
    }
}
