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

import com.shrralis.ssdemo1.dto.MarkerDTO;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MapMarkersServiceImpl implements IMapMarkersService {
	public static final int MULTIPLE = 4;

    private final MapMarkersRepository markerRepository;
    private final IssuesRepository issuesRepository;

	@Autowired
	public MapMarkersServiceImpl(MapMarkersRepository markerRepository, IssuesRepository issuesRepository) {
		this.markerRepository = markerRepository;
		this.issuesRepository = issuesRepository;
	}

    @Override
    public List<MarkerDTO> loadAllMarkers() {

		List<MarkerDTO> list = new ArrayList<>();
        List<MapMarker> markers = markerRepository.findAll();
        for(MapMarker m : markers) {

        	MarkerDTO dto = new MarkerDTO(m.getLat(), m.getLng());
        	int[] types = issuesRepository.getIssueTypeById(m.getId());

        	if(types.length == 1) {
		        dto.setType(types[0]);
	        } else dto.setType(MULTIPLE);
	        list.add(dto);
        }
        return list;
    }

	@Override
	public MapMarker getMarker(double lat, double lng) {
		return markerRepository.getByLatAndLng(lat, lng);
	}

	@Override
    public MapMarker saveMarker(MapMarker marker) {
        return markerRepository.save(marker);
    }
}
