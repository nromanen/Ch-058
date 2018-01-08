package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Issue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto, MultipartFile image);

    Issue getById(Integer id);

    Issue createIssue(MapDataDTO data);

    List<Issue> getAllIssueByMapMarker(int mapMarkerId);
}
