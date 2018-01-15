package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.ImagesRepository;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.shrralis.ssdemo1.security.model.AuthorizedUser.getCurrent;

@Service
@Transactional
public class IssueServiceImpl implements IIssueService {
	public static final int OPENED_TYPE = 1;

	private static final Logger logger = LoggerFactory.getLogger(IssueServiceImpl.class);

    private final IssuesRepository issuesRepository;
    private final MapMarkersRepository mapMarkersRepository;
    private final UsersRepository usersRepository;
    private final ImagesRepository imagesRepository;

	@Autowired
	public IssueServiceImpl(IssuesRepository issuesRepository,
	                        MapMarkersRepository mapMarkersRepository,
	                        UsersRepository usersRepository,
	                        ImagesRepository imagesRepository) {
		this.issuesRepository = issuesRepository;
		this.mapMarkersRepository = mapMarkersRepository;
		this.usersRepository = usersRepository;
		this.imagesRepository = imagesRepository;
	}

    @Override
    public Issue getById(Integer id) {
        return issuesRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Issue saveIssue(MapDataDTO dto, MultipartFile file) {

		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());

	    User user = usersRepository.findOne(getCurrent().getId());

	    boolean closed = dto.getTypeId() != OPENED_TYPE;

	    Image image = parseImage(file);

	    return issuesRepository.save(Issue.Builder.anIssue()
		        .setMapMarker(marker)
		        .setTitle(dto.getTitle())
		        .setText(dto.getDesc())
		        .setAuthor(user)
		        .setImage(image)
		        .setTypeId(dto.getTypeId())
		        .setClosed(closed)
		        .setCreatedAt(LocalDateTime.now())
		        .setUpdatedAt(LocalDateTime.now())
		        .build());
    }

    private Image parseImage(MultipartFile file) {
	    byte[] blob = {};

	    try {
		    blob = file.getBytes();
	    } catch (IOException e) {
		    logger.info("Error while file encoding", e);
	    }

	    Image duplicateImage = imagesRepository.getByHash(DigestUtils.md5Hex(blob));

	    if(duplicateImage == null) {
		    Image image = new Image();

		    String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
		    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		    String uniqueFile = uniqueFileName + "." + extension;

		    image.setSrc(uniqueFile);
		    image.setHash(DigestUtils.md5Hex(blob));

		    File newFile = new File(System.getProperty("catalina.home") + File.separator + uniqueFile);

		    try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
			    stream.write(blob);
		    } catch (IOException e) {
			    logger.info("Error while file saving", e);
		    }
		    return image;
	    }

	    return duplicateImage;
    }

	@Override
	public List<Issue> getAllIssueByMapMarker(int mapMarkerId) {
		return issuesRepository.findByMapMarker_Id(mapMarkerId);
	}
}
