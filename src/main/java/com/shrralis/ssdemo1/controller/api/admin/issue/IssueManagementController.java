package com.shrralis.ssdemo1.controller.api.admin.issue;

import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping/*("/")*/
	public JsonResponse getAll(@PageableDefault(page = 0, size = 10, sort = "title") Pageable pageable) {
		return new JsonResponse(issueService.findAll(pageable).getContent());
	}
//	@GetMapping
//	public Page<Issue> getAll(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size
//			@PageableDefault(page = 0, size = 10) Pageable pageable
//	) {
//		return issueService.findAll(/*pageable*/new PageRequest(page, size));
//	}

	@GetMapping("/{id}")
	public JsonResponse getById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.findById(id));
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteById(@PathVariable int id) throws AbstractCitizenException {
		return new JsonResponse(issueService.deleteById(id));
	}

	@PutMapping("/{id}/{flag}")
	public JsonResponse setStatusById(@PathVariable int id, @PathVariable boolean flag) throws AbstractCitizenException {
		return new JsonResponse(issueService.setStatus(flag, id));
	}

//	@GetMapping("/author/{id}")
//	public JsonResponse getAllByAuthorId(@PathVariable Integer id,
//	                                 @PageableDefault(page = 0, size = 10) Pageable pageable) {
//		return new JsonResponse(issueService.findAuthorId(id, pageable));
//	}
//
//	@GetMapping("/search/{query}")
//	public JsonResponse getAllByTitleOrText(@PathVariable String query,
//	                                       @PageableDefault(page = 0, size = 10) Pageable pageable) {
//		return new JsonResponse(issueService.findByTitleOrText(query, query, pageable));
//	}
//
//	@GetMapping("/opened")
//	public JsonResponse getAllByTypeOpen(@PageableDefault(page = 0, size = 10) Pageable pageable) {
//		return new JsonResponse(issueService.findClosedFalse(pageable));
//	}
//
//	@GetMapping("/closed")
//	public JsonResponse getAllByTypeClose(@PageableDefault(page = 0, size = 10) Pageable pageable) {
//		return new JsonResponse(issueService.findClosedTrue(pageable));
//	}
}

