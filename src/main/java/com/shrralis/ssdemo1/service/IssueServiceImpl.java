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

			if (duplicateImage != null) {
				byte[] duplicateImageFile = getImageInByte(duplicateImage.getSrc());

				if(Arrays.equals(blob, duplicateImageFile)) {
					return duplicateImage;
				}
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
