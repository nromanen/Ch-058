package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/31/18 at 11:10 PM
 */
public interface IImageService {

	byte[] getIssueImageInByte(int issueId) throws IOException;

	byte[] getUserImageInByte(int userId) throws IOException;

	byte[] getImageBySrcInByte(String src) throws BadFieldFormatException;

	Image parseImage(MultipartFile file) throws BadFieldFormatException;
}
