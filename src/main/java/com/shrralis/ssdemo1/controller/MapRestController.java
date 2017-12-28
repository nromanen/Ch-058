/*
 * The following code have been created by Yurii Kiziuk.
 * The code can be used in non-commercial way.
 * For any commercial use it needs an author's agreement.
 * Please contact the author:
 *  - yurakizyuk@gmail.com
 *
 * Copyright (c) 2017 by Yurii Kiziuk.
 */

package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/map", consumes = MediaType.APPLICATION_JSON_VALUE)
public class MapRestController {

    @Autowired
	IMapMarkersService markerService;

    @Autowired
	IIssueService issueService;

    @GetMapping
    public JsonResponse loadAllMarkers() {
        return new JsonResponse(markerService.loadAllMarkers());
    }

	@GetMapping("/getMarker")
	public JsonResponse getMarker(@RequestParam("lat") double lat,
								  @RequestParam("lng") double lng) {
		return new JsonResponse(markerService.getMarker(lat, lng));
	}

    @PostMapping(value = "/saveMarker")
    public JsonResponse saveMarker(@RequestBody MapMarker marker) {
        return new JsonResponse(markerService.saveMarker(marker));
    }

	/*@PostMapping(
			value = "/deleteMarker",
			headers = "Accept=application/json",
			produces = "application/json")
	public JsonResponse deleteMarker(@RequestBody MapMarker marker) {
		return new JsonResponse(service.deleteMarker(marker));
	}*/

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping(value = "/saveIssue")
	public JsonResponse saveData(@RequestBody MapDataDTO data) {

		//data.setAuthorId(AuthorizedUser.getCurrent().getId());
		data.setClosed(false);
		data.setCreatedAt(LocalDateTime.now());

		return new JsonResponse(issueService.saveIssue(data));
	}
}
