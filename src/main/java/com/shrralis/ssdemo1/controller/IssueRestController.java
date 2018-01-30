package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IIssueTypesService;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/issues")
public class IssueRestController {

	private final IIssueService issueService;
	private final IIssueTypesService issueTypesService;
	private final IIssueVotesService issueVotesService;
	private static final String LIKE = "likeVote";
	private static final String DISLIKE = "dislikeVote";

	@Autowired
	public IssueRestController(IIssueService issueService,
	                           IIssueTypesService issueTypesService,
	                           IIssueVotesService issueVotesService) {
		this.issueService = issueService;
		this.issueTypesService = issueTypesService;
		this.issueVotesService = issueVotesService;
	}

	@GetMapping
	public JsonResponse all(/*@PageableDefault(page=0,size=10,sort="title")Pageable pageable*/
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size
	) {
		return new JsonResponse(issueService.findAll(new PageRequest(page, size)));
	}

	@GetMapping("/types")
	public JsonResponse allTypes() {
		return new JsonResponse(issueTypesService.getAll());
	}

	@GetMapping("/{issueId}")
	public JsonResponse issue(@PathVariable("issueId") Integer issueId) {
		return new JsonResponse(issueService.getById(issueId));
	}

	@GetMapping("/{issueId}/is-vote-exist")
	public JsonResponse voteExists(@PathVariable("issueId") Integer issueId) {
		Boolean vote = issueVotesService
				.getByVoterIdAndIssueId(AuthorizedUser.getCurrent().getId(), issueId).getVote();
		return new JsonResponse(vote);
	}

	@DeleteMapping("/{issueId}/vote")
	public void vote(@PathVariable("issueId") Integer issueId) {
		issueVotesService.deleteByVoterIdAndIssueId(AuthorizedUser.getCurrent().getId(), issueId);
	}

	@PostMapping("/{issueId}/{vote}")
	public void vote(@PathVariable("issueId") Integer issueId,
					 @PathVariable("vote") Boolean vote) {
		issueVotesService.insertIssueVote(issueId, AuthorizedUser.getCurrent().getId(), vote);
	}

	@GetMapping("/{issueId}/votes")
	public JsonResponse calculateVotes(@PathVariable("issueId") Integer issueId) {
		Map<String, Long> map = new HashMap<>();
		map.put(LIKE, issueVotesService.countByVoteAndIssue(true, issueId));
		map.put(DISLIKE, issueVotesService.countByVoteAndIssue(false, issueId));
		return new JsonResponse(map);
	}

	@GetMapping(value = "/images/{issueId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] issueImage(@PathVariable("issueId") Integer issueId) throws IOException {
		return issueService.getImageInByte(issueId);
	}
}
