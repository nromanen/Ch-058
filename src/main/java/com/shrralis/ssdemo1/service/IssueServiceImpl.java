package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
import com.shrralis.ssdemo1.repository.*;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.shrralis.ssdemo1.security.model.AuthorizedUser.getCurrent;

@Service
@Transactional
public class IssueServiceImpl implements IIssueService {
	private static final String OPENED_TYPE = "PROBLEM";
	private static final String CATALINA_HOME_NAME = "catalina.home";

	private final IssuesRepository issuesRepository;
	private final MapMarkersRepository mapMarkersRepository;
	private final UsersRepository usersRepository;
	private final ImagesRepository imagesRepository;
	private final IssueTypesRepository issueTypesRepository;

	@Autowired
	public IssueServiceImpl(IssuesRepository issuesRepository,
							MapMarkersRepository mapMarkersRepository,
							UsersRepository usersRepository,
							ImagesRepository imagesRepository,
							IssueTypesRepository issueTypesRepository) {
		this.issuesRepository = issuesRepository;
		this.mapMarkersRepository = mapMarkersRepository;
		this.usersRepository = usersRepository;
		this.imagesRepository = imagesRepository;
		this.issueTypesRepository = issueTypesRepository;
	}

	@Override
	public Issue getById(Integer id) {
		return issuesRepository.findById(id).orElseThrow(NullPointerException::new);
	}

	public Issue saveIssue(MapDataDTO dto, MultipartFile file) throws BadFieldFormatException {

		MapMarker marker = mapMarkersRepository.findOne(dto.getMarkerId());

		User user = usersRepository.findOne(getCurrent().getId());

		boolean closed = !dto.getTypeName().equals(OPENED_TYPE);

		Image image = parseImage(file);

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
		if (issuesRepository.getOne(id) == null) {
			throw new EntityNotExistException(EntityNotExistException.Entity.ISSUE);
		}
		return issuesRepository.deleteById(id);
	}

	@Override
	public Integer setStatus(Boolean flag, Integer id) throws AbstractCitizenException {
		if (issuesRepository.getOne(id) == null) {
			throw new EntityNotExistException(EntityNotExistException.Entity.ISSUE);
		}
		return issuesRepository.setStatus(flag, id);
	}

	public Page<Issue> findClosedTrue(Pageable pageable) {
		return issuesRepository.findByClosedTrue(pageable);
	}

	public Page<Issue> findClosedFalse(Pageable pageable) {
		return issuesRepository.findByClosedFalse(pageable);
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

	@Override
	public byte[] getImageInByte(String src) throws BadFieldFormatException {
		try {
			Path path = Paths.get(System.getProperty(CATALINA_HOME_NAME) + File.separator + src);
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new BadFieldFormatException(e.getMessage());
		}
	}

	private Image parseImage(MultipartFile file) throws BadFieldFormatException {
		try {
			byte[] blob = file.getBytes();

			Image duplicateImage = imagesRepository.getByHash(DigestUtils.md5Hex(blob));

			if (duplicateImage != null && Arrays.equals(blob, getImageInByte(duplicateImage.getSrc()))) {
				return duplicateImage;
			}

			String uniqueFileName = UUID.randomUUID().toString().replace("-", "");
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String uniqueFile = uniqueFileName + "." + extension;

			Image image = new Image();
			image.setSrc(uniqueFile);
			image.setHash(DigestUtils.md5Hex(blob));

			File newFile = new File(System.getProperty(CATALINA_HOME_NAME) + File.separator + uniqueFile);
			FileUtils.writeByteArrayToFile(newFile, blob);

			return image;

		} catch(IOException e) {
			throw new BadFieldFormatException(e.getMessage());
		}
	}

	private Issue.Type getTypeByName(String type) {

		Issue.Type issueType = issueTypesRepository.getByName(type);

		if(issueType == null) {
			issueType = new Issue.Type();
			issueType.setName(type);
		}
		return issueType;

	}
}
