package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto, MultipartFile image) throws BadFieldFormatException;

    Issue getById(Integer id);

    List<Issue> getAllIssueByMapMarker(int mapMarkerId);

    byte[] getImageInByte(Integer issueId) throws IOException;

    byte[] getImageInByte(String src) throws BadFieldFormatException;
}
