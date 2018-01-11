package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class IssueRestController {

    private final IIssueService issueService;
    private final IIssueVotesService issueVotesService;

    @Autowired
    public IssueRestController(IIssueService issueService,IIssueVotesService issueVotesService) {
        this.issueService = issueService;
        this.issueVotesService = issueVotesService;
    }

    @GetMapping(value = "/issues/{issueId}")
    public JsonResponse getIssue(@PathVariable("issueId") Integer issueId) {
        return new JsonResponse(issueService.getById(issueId));
    }

    @GetMapping(value = "/issues/{issueId}/is-vote-exist")
    public JsonResponse getVote(@PathVariable("issueId") Integer issueId, HttpServletResponse response) {
        int userId = 1;
        Boolean vote = issueVotesService.getByVoterIdAndIssueId(userId, issueId).getVote();
        return new JsonResponse(vote);
    }

    @DeleteMapping(value = "/issues/{issueId}/vote")
    public void deleteLike(@PathVariable("issueId") Integer issueId) {
        int userId = 1;
        issueVotesService.deleteByVoterIdAndIssueId(userId, issueId);
    }

    @PostMapping(value = "/issues/{issueId}/{vote}")
    public void addVote(@PathVariable("issueId") Integer issueId,
                        @PathVariable("vote")Boolean vote) {
        int userId = 1;
        issueVotesService.insertIssueVote(issueId, userId, vote);
    }

    @GetMapping(value = "/issues/{issueId}/votes")
    public JsonResponse calculateVote(@PathVariable("issueId") Integer issueId) {
        Map<String, Long> map = new HashMap<>();
        map.put("likeVote", issueVotesService.countByVoteAndIssue(true, issueId));
        map.put("dislikeVote", issueVotesService.countByVoteAndIssue(false, issueId));
        return new JsonResponse(map);
    }

}
