package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/24/17 at 2:15 AM
 */
public class BadFieldFormatException extends AbstractCitizenException {

	private String field;

	public BadFieldFormatException(String field) {
		super("The passed " + field + " parameter has bad format");

		this.field = field;
	}

	public BadFieldFormatException(String field, boolean anyForDefaultMsg) {
		super("The passed " + field + " has bad format");
	}

	public String getField() {
		return field;
	}

	@Override
	public JsonError.Error getError() {
		return JsonError.Error.BAD_FIELD_FORMAT.forField(field);
	}
}
