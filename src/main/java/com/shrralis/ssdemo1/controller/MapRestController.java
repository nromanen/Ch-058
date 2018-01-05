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
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/getMarkerByCoords")
	public JsonResponse getMarkerByCoords(@RequestParam("lat") double lat,
	                                      @RequestParam("lng") double lng) {
		return new JsonResponse(markerService.getMarker(lat, lng));
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/saveMarker")
    public JsonResponse saveMarker(@RequestBody MapMarker marker) {
        return new JsonResponse(markerService.saveMarker(marker));
    }

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping(value = "/saveIssue")
	public JsonResponse saveData(@RequestBody MapDataDTO data) {
		return new JsonResponse(issueService.createIssue(data));
	}

	@GetMapping(value = "/issues/mapMarker/{mapMarkerId}")
	public JsonResponse getIssueByMapMarker(@PathVariable("mapMarkerId") int mapMarkerId) {
		return new JsonResponse(issueService.getAllIssueByMapMarker(mapMarkerId));
	}
}
