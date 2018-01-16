package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;



@RestController
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

    @GetMapping(value = "/issues/{issueId}")
    public JsonResponse getIssue(@PathVariable("issueId") Integer issueId) {
        return new JsonResponse(issueService.getById(issueId));
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/issues/{issueId}/is-vote-exist")
    public JsonResponse getVote(@PathVariable("issueId") Integer issueId, HttpServletResponse response) {
        Boolean vote = issueVotesService.getByVoterIdAndIssueId(AuthorizedUser.getCurrent().getId(), issueId).getVote();
        return new JsonResponse(vote);
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/issues/{issueId}/vote")
    public void deleteLike(@PathVariable("issueId") Integer issueId) {
        issueVotesService.deleteByVoterIdAndIssueId(AuthorizedUser.getCurrent().getId(), issueId);
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/issues/{issueId}/{vote}")
    public void addVote(@PathVariable("issueId") Integer issueId,
                        @PathVariable("vote")Boolean vote) {
        issueVotesService.insertIssueVote(issueId, AuthorizedUser.getCurrent().getId(), vote);
    }

    @GetMapping(value = "/issues/{issueId}/votes")
    public JsonResponse calculateVote(@PathVariable("issueId") Integer issueId) {
        Map<String, Long> map = new HashMap<>();
        map.put(LIKE, issueVotesService.countByVoteAndIssue(true, issueId));
        map.put(DISLIKE, issueVotesService.countByVoteAndIssue(false, issueId));
        return new JsonResponse(map);
    }

    @GetMapping(value = "/images/{issueId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto(@PathVariable("issueId") Integer issueId) throws IOException {
		return issueService.getImageInByte(issueId);
	}
}
