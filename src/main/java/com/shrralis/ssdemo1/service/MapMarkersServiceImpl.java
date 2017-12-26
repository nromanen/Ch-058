package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MapMarkersServiceImpl implements IMapMarkersService {

    @Autowired
    MapMarkersRepository mapMarkersRepository;


    @Override
    public MapMarker findByLatAndLng(Double lat, Double lng) {
        return mapMarkersRepository.findByLatAndLng(lat, lng);
    }
}
