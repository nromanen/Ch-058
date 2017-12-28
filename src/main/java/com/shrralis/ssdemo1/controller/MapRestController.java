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
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/map")
public class MapRestController {

    @Autowired
	IMapMarkersService service;

    @GetMapping(
            value = "",
            headers = "Accept=application/json",
            produces = "application/json")
    public JsonResponse loadAllMarkers() {
        return new JsonResponse(service.loadAllMarkers());
    }

	@GetMapping(
			value = "/getMarker",
			headers = "Accept=application/json",
			produces = "application/json")
	public JsonResponse getMarker(@RequestParam("lat") double lat,
								  @RequestParam("lng") double lng) {
		return new JsonResponse(service.getMarker(lat, lng));
	}

    @PostMapping(
            value = "/saveMarker",
            headers = "Accept=application/json",
            produces = "application/json")
    public JsonResponse saveMarker(@RequestBody MapMarker marker) {
        return service.saveMarker(marker);
    }

	/*@PostMapping(
			value = "/deleteMarker",
			headers = "Accept=application/json",
			produces = "application/json")
	public JsonResponse deleteMarker(@RequestBody MapMarker marker) {
		return new JsonResponse(service.deleteMarker(marker));
	}*/

	@PostMapping(
			value = "/saveData",
			headers = "Accept=application/json",
			produces = "application/json")
	public JsonResponse saveData(@RequestBody MapDataDTO data) {

		//data.setAuthorId(AuthorizedUser.getCurrent().getId()); TODO
		data.setClosed(false);
		data.setCreatedAt(LocalDateTime.now());

		return service.saveData(data);
	}
}
