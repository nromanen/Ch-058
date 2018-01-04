package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;

import java.util.Optional;

public interface IIssueService {

    Issue saveIssue(MapDataDTO dto);
}
