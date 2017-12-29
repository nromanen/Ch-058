/*
 * The following code have been created by Yurii Kiziuk.
 * The code can be used in non-commercial way.
 * For any commercial use it needs an author's agreement.
 * Please contact the author:
 *  - yurakizyuk@gmail.com
 *
 * Copyright (c) 2017 by Yurii Kiziuk.
 */

package com.shrralis.ssdemo1.dto;
import java.time.LocalDateTime;

public class MapDataDTO {
	private Integer mapMarkerId;
	private Integer authorId;
	private String title;
	private String text;
	private Integer typeId;
	private Integer imageId;

	public Integer getMapMarkerId() {
		return mapMarkerId;
	}

	public void setMapMarkerId(Integer mapMarkerId) {
		this.mapMarkerId = mapMarkerId;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
}
