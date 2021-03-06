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

import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.tools.model.JsonError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MapDataDTO {

	@NotNull(message = JsonError.Error.MISSING_FIELD_NAME)
	private Integer markerId;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Size(
			min = Issue.MIN_TITLE_LENGTH, max = Issue.MAX_TITLE_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String title;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Size(
			min = Issue.MIN_TEXT_LENGTH, max = Issue.MAX_TEXT_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String desc;

	@NotNull(message = JsonError.Error.MISSING_FIELD_NAME)
	private Integer typeId;

	public Integer getMarkerId() {
		return markerId;
	}

	public void setMarkerId(Integer markerId) {
		this.markerId = markerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}
