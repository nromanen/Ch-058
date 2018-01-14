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

    @GetMapping(value = "/issues/{issueId}/is-vote-exist")
    public JsonResponse getVote(@PathVariable("issueId") Integer issueId, HttpServletResponse response) {
        int userId = AuthorizedUser.getCurrent() != null ? AuthorizedUser.getCurrent().getId() : -1;
        Boolean vote = issueVotesService.getByVoterIdAndIssueId(userId, issueId).getVote();
        return new JsonResponse(vote);
    }

    @DeleteMapping(value = "/issues/{issueId}/vote")
    public void deleteLike(@PathVariable("issueId") Integer issueId) {
	    int userId = AuthorizedUser.getCurrent() != null ? AuthorizedUser.getCurrent().getId() : -1;
        issueVotesService.deleteByVoterIdAndIssueId(userId, issueId);
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/issues/{issueId}/{vote}")
    public void addVote(@PathVariable("issueId") Integer issueId,
                        @PathVariable("vote")Boolean vote) {
		int userId = AuthorizedUser.getCurrent() != null ? AuthorizedUser.getCurrent().getId() : -1;
        issueVotesService.insertIssueVote(issueId, userId, vote);
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
	    BufferedImage image = ImageIO.read(new File(issueService.getImageSrc(issueId)));
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(image, "jpg", baos);
	    baos.flush();
	    byte[] imageInByte = baos.toByteArray();
	    baos.close();
		return imageInByte;
	}
}
