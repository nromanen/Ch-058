package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.repository.ImagesRepository;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IImageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Arrays;
import java.util.UUID;

import static com.shrralis.ssdemo1.configuration.AppConfig.CATALINA_HOME_NAME;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/31/18 at 11:11 PM
 */
@Service
@Transactional
public class ImageServiceImpl implements IImageService {

	private final ImagesRepository repository;
	private final UsersRepository usersRepository;
	private final IssuesRepository issuesRepository;

	@Autowired
	public ImageServiceImpl(ImagesRepository repository,
	                        UsersRepository usersRepository,
	                        IssuesRepository issuesRepository) {
		this.repository = repository;
		this.usersRepository = usersRepository;
		this.issuesRepository = issuesRepository;
	}

	@Override
	public byte[] getIssueImageInByte(int issueId) throws IOException {
		return getImageBytes(System.getProperty(CATALINA_HOME_NAME) + File.separator
				+ issuesRepository.findOne(issueId).getImage().getSrc());
	}

	@Override
	public byte[] getUserImageInByte(int userId) throws IOException {
		return getImageBytes(System.getProperty(CATALINA_HOME_NAME) + File.separator
				+ usersRepository.findOne(userId).getImage().getSrc());
	}

	@Override
	public byte[] getImageBySrcInByte(String src) throws BadFieldFormatException {
		try {
			Path path = Paths.get(System.getProperty(CATALINA_HOME_NAME) + File.separator + src);

			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new BadFieldFormatException(e.getMessage());
		}
	}

	@Override
	public Image parseImage(MultipartFile file) throws BadFieldFormatException {
		try {
			byte[] blob = file.getBytes();

			Image duplicateImage = repository.getByHash(DigestUtils.md5Hex(blob));

			if (duplicateImage != null && Arrays.equals(blob, getImageBySrcInByte(duplicateImage.getSrc()))) {
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
		} catch (IOException e) {
			throw new BadFieldFormatException(e.getMessage());
		}
	}

	private byte[] getImageBytes(String filename) throws IOException {
		String extension = FilenameUtils.getExtension(filename);
		BufferedImage image = ImageIO.read(new File(filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ImageIO.write(image, extension, baos);
		baos.flush();

		byte[] imageInByte = baos.toByteArray();

		baos.close();
		return imageInByte;
	}
}
