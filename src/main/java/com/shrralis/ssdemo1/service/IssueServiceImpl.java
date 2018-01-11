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

	@Value("${imageStorage}")
	private String imageStorage;

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

    public Issue saveIssue(MapDataDTO dto, MultipartFile file)  {

		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());

	    User user = usersRepository.findOne(getCurrent().getId());

	    boolean closed = dto.getTypeId() != 1;

	    Image image;
	    byte[] fileBytes = {};
	    try {
		    fileBytes = file.getBytes();
	    } catch (IOException e) {
		    logger.info("Error while file encoding", e);
	    }
	    Image duplicateImage = imagesRepository.getByHash(DigestUtils.md5Hex(fileBytes));
	    if(duplicateImage != null) {
			image = duplicateImage;
	    } else {
		    image = new Image();

		    String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String uniqueFile = uniqueFileName + "." + extension;

		    image.setSrc(uniqueFile);
		    image.setHash(DigestUtils.md5Hex(fileBytes));

		    File newFile = new File(imageStorage + uniqueFile);
		    try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
			    stream.write(fileBytes);
		    } catch (IOException e) {
			    logger.info("Error while file saving", e);
		    }
	    }

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

	@Override
	public List<Issue> getAllIssueByMapMarker(int mapMarkerId) {
		return issuesRepository.findByMapMarker_Id(mapMarkerId);
	}

	@Override
	public Issue findById(Integer id) {
		return issuesRepository.findById(id).orElseThrow(() -> new NullPointerException());
	}

	@Override
	public List<Issue> findByTitleOrTextContainingAllIgnoreCase(String title, String text) {
		List<Issue> res = issuesRepository.findByTitleOrTextContainingAllIgnoreCase(title, title);
		if (res.isEmpty()) {
			throw new NullPointerException();
		} else {
			return res;
		}
	}

	@Override
	public List<Issue> findByAuthor_Id(Integer id) {
		List<Issue> res = issuesRepository.findByAuthor_Id(id);
		if (res.isEmpty()) {
			throw new NullPointerException();
		} else {
			return res;
		}
	}

	@Override
	public List<Issue> findAll() {
		List<Issue> res = issuesRepository.findAll();
		if (res.isEmpty()) {
			throw new NullPointerException();
		} else {
			return res;
		}
	}

	@Override
	public void deleteById(Integer id) {
		issuesRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void setStatus(Boolean closed, Integer id) {
		issuesRepository.setStatus(closed, id);
	}

	public List<Issue> findByClosedTrue() {
		return issuesRepository.findByClosedTrue();
	}

	public List<Issue> findByClosedFalse() {
		return issuesRepository.findByClosedFalse();
	}
}
