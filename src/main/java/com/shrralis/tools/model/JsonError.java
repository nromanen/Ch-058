package com.shrralis.tools.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Objects;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:11 AM
 */
public class JsonError {
	private int errno = Error.NO_ERROR.getId();
	private String errmsg = Error.NO_ERROR.getMessage();
	private String field;

	public JsonError() {
	}

	public JsonError(Error error) {
		setError(error);
	}

	public void setError(Error e) {
		errno = e.getId();
		errmsg = e.getMessage();
		field = e.getField();
		e.field = null;
	}

	public JsonError(String message) {
		setMessage(message);
	}

	public int getErrno() {
		return errno;
	}

	public void setMessage(String message) {
		errno = -1;
		errmsg = message;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public String getField() {
		return field;
	}

	@Override
	public int hashCode() {
		return Objects.hash(errno, errmsg, field);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JsonError) {
			JsonError error = (JsonError) obj;

			return error.errno == errno
					&& error.errmsg.equals(errmsg)
					&& error.field.equals(field);
		}
		return false;
	}

	@Override
	public String toString() {
		return "{\n" +
				"\terrno: " + errno + ",\n" +
				"\terrmsg: \"" + errmsg + "\",\n" +
				"\tfield: \"" + field + "\"\n" +
				'}';
	}

	public enum Error {
		NO_ERROR(0, "No errors"),
		UNEXPECTED(1, "Unexpected error"),
		ACCESS_DENIED(2, "Access denied"),
		IMAGE_ALREADY_EXISTS(3, "Image already exists"),
		MAP_MARKER_ALREADY_EXISTS(4, "Map marker already exists"),
		USER_ALREADY_EXISTS(5, "User already exists"),
		MISSING_FIELD(6, "Missing field"),
		BAD_FIELD_FORMAT(7, "Bad field format"),
		USER_NOT_EXIST(8, "User with current login doesn't exist"),
		BAD_CREDENTIALS(9, "Password is wrong"),;

		public static final String NO_ERROR_NAME = "NO_ERROR";
		public static final String UNEXPECTED_NAME = "UNEXPECTED";
		public static final String ACCESS_DENIED_NAME = "ACCESS_DENIED";
		public static final String IMAGE_ALREADY_EXISTS_NAME = "IMAGE_ALREADY_EXISTS";
		public static final String MAP_MARKER_ALREADY_EXISTS_NAME = "MAP_MARKER_ALREADY_EXISTS";
		public static final String USER_ALREADY_EXISTS_NAME = "USER_ALREADY_EXISTS";
		public static final String MISSING_FIELD_NAME = "MISSING_FIELD";
		public static final String BAD_FIELD_FORMAT_NAME = "BAD_FIELD_FORMAT";
		public static final String USER_NOT_EXIST_NAME = "USER_NOT_EXIST";
		public static final String BAD_CREDENTIALS_NAME = "BAD_CREDENTIALS";

		private static final ArrayList<Error> lookup = new ArrayList<>();

		static {
			for (Error err : EnumSet.allOf(Error.class)) {
				lookup.add(err.getId(), err);
			}
		}

		private final int id;
		private final String message;
		private String field;

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
			return message;
		}

		public String getField() {
			return field;
		}

		public Error forField(String field) {
			switch (this) {
				case MISSING_FIELD:
				case BAD_FIELD_FORMAT:
				case USER_ALREADY_EXISTS:
					this.field = field;

					break;
				default:
					break;
			}
			return this;
		}
	}
}
