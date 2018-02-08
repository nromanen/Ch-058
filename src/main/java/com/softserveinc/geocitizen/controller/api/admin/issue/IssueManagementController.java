package com.softserveinc.geocitizen.controller.api.admin.issue;

import com.softserveinc.geocitizen.entity.Issue;
import com.softserveinc.geocitizen.exception.AbstractCitizenException;
import com.softserveinc.geocitizen.service.interfaces.IIssueService;
import com.softserveinc.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/issues")
public class IssueManagementController {

	private final IIssueService issueService;

	@Autowired
	public IssueManagementController(IIssueService issueService) {
		this.issueService = issueService;
	}

	@GetMapping
	public JsonResponse getAll(@PageableDefault(page = 0, size = 10, sort = "title") Pageable pageable) {
		Page<Issue> issues = issueService.findAll(pageable);
		return JsonResponse.Builder.aJsonResponse()
				.setData(issues.getContent())
				.setCount(issues.getTotalElements())
				.build();
	}

	@GetMapping("/{id}")
	public JsonResponse getById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.findById(id));
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.deleteById(id));
	}

	@PutMapping("/{id}/close")
	public JsonResponse setClosedStatusById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.toggleClosed(id));
	}

	@PutMapping("/{id}/hide")
	public JsonResponse setHiddenStatusById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.toggleHidden(id));
	}

	@GetMapping("/author/{id}")
	public JsonResponse getAllByAuthorId(@PathVariable int id,
	                                     @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
		Page<Issue> issues = issueService.findAuthorId(id, pageable);
		return JsonResponse.Builder.aJsonResponse()
				.setData(issues.getContent())
				.setCount(issues.getTotalElements())
				.build();
	}

	@GetMapping("/search/{query}")
	public JsonResponse getAllByTitleOrText(@PathVariable String query,
	                                        @PageableDefault(page = 0, size = 10, sort = "title") Pageable pageable) {
		Page<Issue> issues = issueService.findByTitleOrText(query, query, query, pageable);
		return JsonResponse.Builder.aJsonResponse()
				.setData(issues.getContent())
				.setCount(issues.getTotalElements())
				.build();
	}

	@GetMapping("/opened")
	public JsonResponse getAllByTypeOpen(@PageableDefault(page = 0, size = 10, sort = "author") Pageable pageable) {
		Page<Issue> issues = issueService.findClosedFalse(pageable);
		return JsonResponse.Builder.aJsonResponse()
				.setData(issues.getContent())
				.setCount(issues.getTotalElements())
				.build();
	}

	@GetMapping("/closed")
	public JsonResponse getAllByTypeClose(@PageableDefault(page = 0, size = 10, sort = "author") Pageable pageable) {
		Page<Issue> issues = issueService.findClosedTrue(pageable);
		return JsonResponse.Builder.aJsonResponse()
				.setData(issues.getContent())
				.setCount(issues.getTotalElements())
				.build();
	}
}

