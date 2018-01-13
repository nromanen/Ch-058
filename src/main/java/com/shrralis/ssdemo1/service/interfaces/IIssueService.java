package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.entity.Issue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto, MultipartFile image);

    Issue getById(Integer id);

    List<Issue> getAllIssueByMapMarker(int mapMarkerId);

    String getImageSrc(Integer issueId);
}
