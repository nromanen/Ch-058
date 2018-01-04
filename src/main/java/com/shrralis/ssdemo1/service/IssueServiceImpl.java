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
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

	    Issue issue = new Issue();
        issue.setMapMarker(marker);
	    issue.setTitle(data.getTitle());
	    issue.setText(data.getText());

	    User user = usersRepository.findById(getCurrent().getId()).orElse(null);
        issue.setAuthor(user);

	    Image image = new Image();
	    String imgPath = DigestUtils.md5Hex(data.getImage());
	    image.setSrc(imgPath);
	    image.setHash("hashhbshhashhashnashhxshhashhash");
	    issue.setImage(image);

        issue.setTypeId(data.getTypeId());
        boolean isClosed = data.getTypeId() != 1;
        issue.setClosed(isClosed);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        return issuesRepository.save(issue);
    }

	@Override
	public List<Issue> getAllIssueByMapMarker(int mapMarkerId) {
		return issuesRepository.findByMapMarker_Id(mapMarkerId);
	}
}
