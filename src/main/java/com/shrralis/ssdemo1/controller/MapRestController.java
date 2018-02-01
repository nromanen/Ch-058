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
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.IllegalParameterException;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/map")
public class MapRestController {

	private final IMapMarkersService markerService;
	private final IIssueService issueService;

	@Autowired
	public MapRestController(IMapMarkersService markerService,
	                         IIssueService issueService) {
		this.markerService = markerService;
		this.issueService = issueService;
	}

	@GetMapping
	public JsonResponse allMarkers() {
		return new JsonResponse(markerService.loadAllMarkers());
	}

	@GetMapping("/marker/{lat}/{lng}/")
	public JsonResponse markerByCoords(@PathVariable("lat") double lat,
									   @PathVariable("lng") double lng) {
		return new JsonResponse(markerService.getMarker(lat, lng));
	}

	@PostMapping("/marker")
	public JsonResponse marker(@RequestBody final MapMarker marker) {
		return new JsonResponse(markerService.saveMarker(marker));
	}

	@PostMapping("/issue")
	public JsonResponse issue(@RequestParam("file") MultipartFile image,
							  @Valid @ModelAttribute MapDataDTO dto) throws AbstractCitizenException {
		if (image == null) {
			throw new IllegalParameterException("file");
		}
		return new JsonResponse(issueService.saveIssue(dto, image));
	}

	@GetMapping("/issues/mapMarker/{mapMarkerId}")
	public JsonResponse issueByMapMarker(@PathVariable("mapMarkerId") int mapMarkerId) {
		return new JsonResponse(issueService.getAllIssueByMapMarker(mapMarkerId));
	}
}