package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.MapMarker;

public interface IMapMarkersService {
    MapMarker findByLatAndLng(Double lat, Double lng);
}
