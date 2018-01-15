package com.shrralis.ssdemo1.controller.api.admin.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
//@Secured({"ROLE_ADMIN"})
@RequestMapping(value = "/admin/issues")
public class IssueManagementController {

    @Autowired
    IIssueService issueService;

    @GetMapping({"/{id}"})
    public JsonResponse getById(@PathVariable Integer id) {
        return new JsonResponse(issueService.findById(id));
    }

    @GetMapping({"/search/{q}"})
    public JsonResponse getByTitleOrText(@PathVariable String q) {
        return new JsonResponse(issueService.findByTitleOrTextContainingAllIgnoreCase(q, q));
    }

    @GetMapping({"/"})
    public JsonResponse getIssues() {
        return new JsonResponse(issueService.findAll());
    }

    @GetMapping({"/author/{id}"})
    public JsonResponse getByAuthor(@PathVariable Integer id) {
        return new JsonResponse(issueService.findByAuthor_Id(id));
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable Integer id) {
        issueService.deleteById(id);
    }

    @PutMapping({"/{id}/{b}"})
    public void close(@PathVariable Integer id, @PathVariable Boolean b) {
        issueService.setStatus(b, id);
    }

    @GetMapping({"/open"})
    public JsonResponse  getOpened(@PathVariable String t) {
        switch (t.toLowerCase()) {
            case "open":
                return new JsonResponse(issueService.findByClosedFalse());
            case "close":
                return new JsonResponse(issueService.findByClosedTrue());
            default:
                return new JsonResponse(0);
        }
    }
}

