package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IIssueVotesService;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class IssueRestController {
    @Autowired
    IIssueService issueService;
    @Autowired
    IMapMarkersService mapMarkersService;
    @Autowired
    IIssueVotesService issueVotesService;

    @RequestMapping(value = "/issue/{issueId}")
    public Issue getIssue(@PathVariable("issueId") Integer issueId) {
        return issueService.getById(issueId).get();
    }

    @RequestMapping(value = "isVote/{issueId}")
    public Object getVote(@PathVariable("issueId") Integer issueId, HttpServletResponse response) {
        int userId = 1;
        Boolean vote = issueVotesService.getByVoterIdAndIssueId(userId, issueId).get().getVote();
        if (vote != null) {
            return vote;
        }
        else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "deleteVote/{issueId}")
    public void deleteLike(@PathVariable("issueId") Integer issueId) {
        int userId = 1;
        issueVotesService.deleteByVoterIdAndIssueId(userId, issueId);
    }

    @RequestMapping(value = "addVote/{issueId}/{vote}")
    public void addVote(@PathVariable("issueId") Integer issueId,
                                        @PathVariable("vote")Boolean vote) {
        int userId = 1;
        issueVotesService.insertIssueVote(issueId, userId, vote);
    }

    @RequestMapping(value = "calculateVote/{issueId}")
    public Map<String, Integer> calculateVote(@PathVariable("issueId") Integer issueId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("likeVote", issueVotesService.countByVoteAndIssue(true, issueId));
        map.put("dislikeVote", issueVotesService.countByVoteAndIssue(false, issueId));
        return map;
    }
}
