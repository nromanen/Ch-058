package com.shrralis.tools.model;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:11 AM
 */
public class JsonError {
	private int errno = Error.NO_ERROR.getId();
	private String errmsg = Error.NO_ERROR.getMessage();

	public JsonError() {
	}

	public JsonError(Error error) {
		setError(error);
	}

	public void setError(Error e) {
		errno = e.getId();
		errmsg = e.getMessage();
	}

	public JsonError(String message) {
		setMessage(message);
	}

	public void setMessage(String message) {
		errno = -1;
		errmsg = message;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public int getErrno() {
		return errno;
	}

	@Override
	public String toString() {
		return "{\n" +
				"\t\"errno\": " + errno + ",\n" +
				"\t\"errmsg\": \"" + errmsg + "\"\n" +
				"}";
	}

	public enum Error {
		NO_ERROR(0, "No errors"),
		UNEXPECTED(1, "Unexpected error"),
		ACCESS_DENIED(2, "Access denied"),
		IMAGE_ALREADY_EXISTS(3, "Image already exists"),
		MAP_MARKER_ALREADY_EXISTS(4, "Map marker already exists"),
		USER_ALREADY_EXISTS(5, "User already exists"),
		MISSING_PARAMETER(6, "Some parameter isn't present"),
		BAD_PARAMETER_FORMAT(7, "Bad login (username) format"),;

		private static final ArrayList<Error> lookup = new ArrayList<>();

		static {
			for (Error err : EnumSet.allOf(Error.class)) {
				lookup.add(err.getId(), err);
			}
		}

		private final int id;
		private final String message;
		private String paramName;

		Error(int id, String message) {
			this.id = id;
			this.message = message;
		}

		public static Error get(int id) {
			return lookup.get(id);
		}

		public int getId() {
			return id;
		}

		public String getMessage() {
			return paramName == null ? message : paramName;
		}

		public Error setParam(String paramName) {
			if (id == MISSING_PARAMETER.id ||
					id == BAD_PARAMETER_FORMAT.id) {
				this.paramName = paramName;
			}
			return this;
		}
	}
}
