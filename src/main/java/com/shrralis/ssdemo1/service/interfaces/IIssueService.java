package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto, MultipartFile image);

    Issue getById(Integer id);

    List<Issue> getAllIssueByMapMarker(int mapMarkerId);

    Issue findById(Integer id);

    List<Issue> findByTitleOrTextContainingAllIgnoreCase(String title, String text);

    List<Issue> findByAuthor_Id(Integer id);

    List<Issue> findAll();

    void deleteById(Integer id);

    void setStatus(Boolean closed, Integer id);

    List<Issue> findByClosedTrue();

    List<Issue> findByClosedFalse();

    byte[] getImageInByte(Integer issueId) throws IOException;
}
