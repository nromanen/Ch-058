package com.shrralis.ssdemo1.controller.api.admin.issue;

import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
	public JsonResponse allIssues() {
		return new JsonResponse(issueService.findAll());
	}

	@GetMapping("/{id}")
	public JsonResponse issueById(@PathVariable Integer id) throws AbstractCitizenException {
		return new JsonResponse(issueService.findById(id));
	}

	@DeleteMapping("/{id}")
	public void issue(@PathVariable Integer id) {
		issueService.deleteById(id);
	}

	@GetMapping("/opened")
	public JsonResponse openedIssues() {
		return new JsonResponse(issueService.findClosedFalse());
	}

	@GetMapping("/closed")
	public JsonResponse closedIssues() {
		return new JsonResponse(issueService.findClosedTrue());
	}

	@PutMapping("/{id}/{flag}")
	public void close(@PathVariable Integer id, @PathVariable Boolean flag) {
		issueService.setStatus(flag, id);
	}

	@GetMapping("/author/{id}")
	public JsonResponse issueByAuthor(@PathVariable Integer id) {
		return new JsonResponse(issueService.findAuthorId(id));
	}

	@GetMapping("/search/{query}")
	public JsonResponse issueByTitleOrText(@PathVariable String query) {
		return new JsonResponse(issueService.findTitleOrTextContaining(query, query));
	}
}

