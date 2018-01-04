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

import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MapMarkersServiceImpl implements IMapMarkersService {

    private final MapMarkersRepository repository;

	@Autowired
	public MapMarkersServiceImpl(MapMarkersRepository repository) {
		this.repository = repository;
	}

    @Override
    public List<MapMarker> loadAllMarkers() {
        return repository.findAll();
    }

	@Override
	public MapMarker getMarker(double lat, double lng) {
		return repository.getByLatAndLng(lat, lng)
				.orElseThrow(() ->  new IllegalStateException("Marker not found"));
	}

	@Override
    public MapMarker saveMarker(MapMarker marker) {
        return repository.save(marker);
    }
}
