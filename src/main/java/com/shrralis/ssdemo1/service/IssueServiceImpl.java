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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final int OPENED_TYPE = 1;
	private static final String CATALINA_HOME_NAME = "catalina.home";

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

    public Issue saveIssue(MapDataDTO dto, MultipartFile file) throws IOException{

		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());

	    User user = usersRepository.findOne(getCurrent().getId());

	    boolean closed = dto.getTypeId() != OPENED_TYPE;

	    Issue.Type type = dto.getType();

	    Image image = parseImage(file);

	    return issuesRepository.save(Issue.Builder.anIssue()
		        .setMapMarker(marker)
		        .setTitle(dto.getTitle())
		        .setText(dto.getDesc())
		        .setAuthor(user)
		        .setImage(image)
		        .setType()
		        .setClosed(closed)
		        .setCreatedAt(LocalDateTime.now())
		        .setUpdatedAt(LocalDateTime.now())
		        .build());
    }

    private Image parseImage(MultipartFile file) throws IOException {

	    byte[] blob = file.getBytes();
	    Image duplicateImage = imagesRepository.getByHash(DigestUtils.md5Hex(blob));

	    if(duplicateImage == null) {
		    String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
		    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		    String uniqueFile = uniqueFileName + "." + extension;

		    Image image = new Image();
		    image.setSrc(uniqueFile);
		    image.setHash(DigestUtils.md5Hex(blob));

		    File newFile = new File(System.getProperty(CATALINA_HOME_NAME) + File.separator + uniqueFile);
		    FileUtils.writeByteArrayToFile(newFile, blob);

		    return image;
	    }
	    return duplicateImage;
    }

	@Override
	public List<Issue> getAllIssueByMapMarker(int mapMarkerId) {
		return issuesRepository.findByMapMarker_Id(mapMarkerId);
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
