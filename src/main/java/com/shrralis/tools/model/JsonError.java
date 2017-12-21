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
		NO_ERROR(0, "no error"),
		UNEXPECTED(1, "unexpected error"),
		EMPTY_LOGIN(2, "empty login"),
		EMPTY_PASSWORD(3, "empty password"),
		USER_DOES_NOT_EXIST(4, "user doesn't exist"),
		WRONG_PASSWORD(5, "wrong password"),
		BAD_ACCESS_TOKEN(6, "bad access token"),
		INACTIVE_ACCESS_TOKEN(7, "inactive access token"),;

		private static final ArrayList<Error> lookup = new ArrayList<>();

		static {
			for (Error e : EnumSet.allOf(Error.class)) {
				lookup.add(e.getId(), e);
			}
		}

		private final int id;
		private final String message;

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
	}
}
