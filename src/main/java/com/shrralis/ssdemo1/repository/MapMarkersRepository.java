/*
 * The following code have been created by Yurii Kiziuk.
 * The code can be used in non-commercial way.
 * For any commercial use it needs an author's agreement.
 * Please contact the author:
 *  - yurakizyuk@gmail.com
 *
 * Copyright (c) 2017 by Yurii Kiziuk.
 */

package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapMarkersRepository extends JpaRepository<MapMarker, Integer> {
	// custom query that used for getting map marker id
	MapMarker getByLatAndLng(double lat, double lng);
}
