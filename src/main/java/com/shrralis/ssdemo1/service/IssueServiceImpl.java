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

import static com.shrralis.ssdemo1.security.model.AuthorizedUser.getCurrent;

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
    public Issue saveIssue(MapDataDTO dto)  {

		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());

	    User user = usersRepository.findOne(getCurrent().getId());

	    boolean closed = dto.getTypeId() != 1;

	    Image image = new Image();
	    String imgPath = DigestUtils.md5Hex(dto.getImage());
	    image.setSrc(imgPath);
	    image.setHash("hashhbshhashhashnashhxshhashhash");

	    return issuesRepository.save(Issue.Builder.anIssue()
		        .setMapMarker(marker)
		        .setTitle(dto.getTitle())
		        .setText(dto.getText())
		        .setAuthor(user)
		        .setImage(image)
		        .setTypeId(dto.getTypeId())
		        .setClosed(closed)
		        .setCreatedAt(LocalDateTime.now())
		        .setUpdatedAt(LocalDateTime.now())
		        .build());
    }
}
