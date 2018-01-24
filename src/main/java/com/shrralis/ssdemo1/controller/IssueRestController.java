package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/issues")
public class IssueRestController {

	private final IIssueService issueService;
	private final IIssueVotesService issueVotesService;
	private static final String LIKE = "likeVote";
	private static final String DISLIKE = "dislikeVote";

	@Autowired
	public IssueRestController(IIssueService issueService,
							   IIssueVotesService issueVotesService) {
		this.issueService = issueService;
		this.issueVotesService = issueVotesService;
	}

	@GetMapping
	public JsonResponse allIssues() {
		return new JsonResponse(issueService.findAll());
	}

	@GetMapping("/{issueId}")
	public JsonResponse issue(@PathVariable("issueId") Integer issueId) {
		return new JsonResponse(issueService.getById(issueId));
	}

	@GetMapping("/{issueId}/is-vote-exist")
	public JsonResponse voteExists(@PathVariable("issueId") Integer issueId) {
		Boolean vote = issueVotesService.getByVoterIdAndIssueId(AuthorizedUser.getCurrent().getId(), issueId).getVote();
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
