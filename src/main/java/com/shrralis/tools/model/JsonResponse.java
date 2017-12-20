package com.shrralis.tools.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:10 AM
 */
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
