/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.tools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:10 AM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse {
	public static final int OK = 0;

	private Integer result;
	private JsonError error;
	private ArrayList<Object> data;

	public JsonResponse(Object data) {
		this.data = new ArrayList<>();

		if (data instanceof Collection) {
			this.data.addAll((Collection) data);
		} else {
			this.data.add(data);
		}

		result = OK;
	}

	public JsonResponse(int result) {
		this.result = result;
	}

	public JsonResponse(JsonError.Error error) {
		this.error = new JsonError(error);
	}

	public Integer getResult() {
		return result;
	}

	public JsonError getError() {
		return error;
	}

	public ArrayList<Object> getData() {
		return data;
	}
}
