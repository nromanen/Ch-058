package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.shrralis.ssdemo1.security.model.AuthorizedUser.getCurrent;

@Service
@Transactional
public class IssueServiceImpl implements IIssueService {
	public static final int OPENED_TYPE = 1;
	private static final String CATALINA_HOME_NAME = "catalina.home";

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
	    byte[] fileBytes = {};

	    try {
		    fileBytes = file.getBytes();
	    } catch (IOException e) {
		    logger.info("Error while file encoding", e);
	    }

	    Image duplicateImage = imagesRepository.getByHash(DigestUtils.md5Hex(fileBytes));

	    if(duplicateImage == null) {
		    Image image = new Image();

		    String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
		    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		    String uniqueFile = uniqueFileName + "." + extension;

		    image.setSrc(uniqueFile);
		    image.setHash(DigestUtils.md5Hex(fileBytes));

		    File newFile = new File(System.getProperty(CATALINA_HOME_NAME) + File.separator + uniqueFile);
		    try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
			    stream.write(fileBytes);
		    } catch (IOException e) {
			    logger.info("Error while file saving", e);
		    }
		    return image;
	    } else {
		    return duplicateImage;
	    }
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
	public List<Issue> findTitleOrTextContaining(String title, String text) {
		return issuesRepository.findByTitleOrTextContainingAllIgnoreCase(title, text);
	}

	@Override
	public List<Issue> findAuthorId(Integer id) {
		return issuesRepository.findByAuthor_Id(id);
	}

	@Override
	public List<Issue> findAll() {
		return issuesRepository.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		issuesRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void setStatus(Boolean flag, Integer id) {
		issuesRepository.setStatus(flag, id);
	}

	public List<Issue> findClosedTrue() {
		return issuesRepository.findByClosedTrue();
	}

	public List<Issue> findClosedFalse() {
		return issuesRepository.findByClosedFalse();
	}

	@Override
	public byte[] getImageInByte(Integer issueId) throws IOException {
		String fileName = System.getProperty(CATALINA_HOME_NAME) + File.separator + issuesRepository.findOne(issueId).getImage().getSrc();
		String extension = FilenameUtils.getExtension(fileName);
		BufferedImage image = ImageIO.read(new File(fileName));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, extension, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
}
