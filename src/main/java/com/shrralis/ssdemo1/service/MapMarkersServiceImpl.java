/*
 * The following code have been created by Yurii Kiziuk.
 * The code can be used in non-commercial way.
 * For any commercial use it needs an author's agreement.
 * Please contact the author:
 *  - yurakizyuk@gmail.com
 *
 * Copyright (c) 2017 by Yurii Kiziuk.
 */

package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MapMarkersServiceImpl implements IMapMarkersService {

    @Resource
    private MapMarkersRepository repository;

    @Override
    public MapMarker findByLatAndLng(Double lat, Double lng) {
        return repository.findByLatAndLng(lat, lng);
    }

    @Override
    public JsonResponse loadAllMarkers() {
        return new JsonResponse(repository.findAll());
    }

    @Override
    public JsonResponse getMarker(double lat, double lng) {
        return new JsonResponse(repository.getByLatAndLng(lat, lng));
    }

    @Override
    public JsonResponse saveMarker(MapMarker marker) {
        return new JsonResponse(repository.save(marker));
    }

    // currently not in use
    @Override
    public JsonResponse saveData(MapDataDTO data) {
        return null;
    }
}
