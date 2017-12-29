/*
 * The following code have been created by Yurii Kiziuk.
 * The code can be used in non-commercial way.
 * For any commercial use it needs an author's agreement.
 * Please contact the author:
 *  - yurakizyuk@gmail.com
 *
 * Copyright (c) 2017 by Yurii Kiziuk.
 */

package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.MapDataDTO;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.tools.model.JsonResponse;

import java.util.List;

public interface IMapMarkersService {
    List<MapMarker> loadAllMarkers();
    MapMarker getMarker(int id);
    MapMarker saveMarker(MapMarker marker);
    void deleteMarker(int id);
}
