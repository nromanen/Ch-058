package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.Issue;

import java.util.List;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/30/18 at 1:04 PM
 */
public interface IIssueTypesService {

	List<Issue.Type> getAll();
}
