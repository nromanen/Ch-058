package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto, MultipartFile image);

    Issue getById(Integer id);

    List<Issue> getAllIssueByMapMarker(int mapMarkerId);

    Issue findById(Integer id) throws AbstractCitizenException;

    List<Issue> findTitleOrTextContaining(String title, String text);

    List<Issue> findAuthorId(Integer id);

    List<Issue> findAll();

    void deleteById(Integer id);

    void setStatus(Boolean flag, Integer id);

    List<Issue> findClosedTrue();

    List<Issue> findClosedFalse();

    byte[] getImageInByte(Integer issueId) throws IOException;
}
