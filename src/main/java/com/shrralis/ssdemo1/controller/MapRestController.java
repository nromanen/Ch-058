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
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
public class MapRestController {

	private final IMapMarkersService markerService;
	private final IIssueService issueService;

    @Autowired
	public MapRestController(IMapMarkersService markerService, IIssueService issueService) {
    	this.markerService = markerService;
    	this.issueService = issueService;
	}

    @GetMapping
    public JsonResponse loadAllMarkers() {
        return new JsonResponse(markerService.loadAllMarkers());
    }

	@GetMapping("/marker/{lat}/{lng}/")
	public JsonResponse getMarkerByCoords(@PathVariable("lat") double lat,
	                                      @PathVariable("lng") double lng) {
		return new JsonResponse(markerService.getMarker(lat, lng));
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/marker")
    public JsonResponse saveMarker(@RequestBody final MapMarker marker) {
        return new JsonResponse(markerService.saveMarker(marker));
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/issue")
	public JsonResponse saveIssue(@RequestParam("file") MultipartFile image,
	                              @Valid @ModelAttribute MapDataDTO dto) {
		if(image != null) {
			return new JsonResponse(issueService.saveIssue(dto, image));
		}
		return new JsonResponse(JsonError.Error.IMAGE_NOT_EXIST);
	}
}
