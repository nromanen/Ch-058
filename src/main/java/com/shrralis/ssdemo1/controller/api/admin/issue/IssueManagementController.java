package com.shrralis.ssdemo1.controller.api.admin.issue;

import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/issues")
public class IssueManagementController {

	@Autowired
	IIssueService issueService;

	@GetMapping("/{id}")
	public JsonResponse getById(@PathVariable Integer id) throws AbstractCitizenException {
		return new JsonResponse(issueService.findById(id));
	}

	@GetMapping("/search/{query}")
	public JsonResponse getByTitleOrText(@PathVariable String query) {
		return new JsonResponse(issueService.findTitleOrTextContaining(query, query));
	}

	@GetMapping("/")
	public JsonResponse getIssues() {
		return new JsonResponse(issueService.findAll());
	}

	@GetMapping("/author/{id}")
	public JsonResponse getByAuthor(@PathVariable Integer id) {
		return new JsonResponse(issueService.findAuthorId(id));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		issueService.deleteById(id);
	}

	@PutMapping("/{id}/{flag}")
	public void close(@PathVariable Integer id, @PathVariable Boolean flag) {
		issueService.setStatus(flag, id);
	}

	@GetMapping("/opened")
	public JsonResponse getOpened() {
		return new JsonResponse(issueService.findClosedFalse());
	}

	@GetMapping("/closed")
	public JsonResponse getClosed() {
		return new JsonResponse(issueService.findClosedTrue());
	}
}

