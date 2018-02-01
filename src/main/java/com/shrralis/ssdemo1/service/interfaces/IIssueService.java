package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIssueService {

	Issue saveIssue(MapDataDTO dto, MultipartFile image) throws BadFieldFormatException;

	Issue getById(Integer id);

	List<Issue> getAllIssueByMapMarker(int mapMarkerId);

	Issue findById(Integer id) throws AbstractCitizenException;

	Page<Issue> findByTitleOrText(String title, String text, String author, Pageable pageable);

	Page<Issue> findAuthorId(Integer id, Pageable pageable);

	Page<Issue> findAll(Pageable pageable);

	Integer deleteById(Integer id) throws AbstractCitizenException;

	Integer setStatus(Boolean flag, Integer id) throws AbstractCitizenException;

	Page<Issue> findClosedTrue(Pageable pageable);

	Page<Issue> findClosedFalse(Pageable pageable);
}
