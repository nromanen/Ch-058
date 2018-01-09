package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

import static com.shrralis.tools.model.JsonError.Error.ILLEGAL_PARAMETER;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/3/18 at 3:12 PM
 */
public class IllegalParameterException extends AbstractCitizenException {

	private final String field;

	public IllegalParameterException(String field) {
		this.field = field;
	}

	public IllegalParameterException(String field, String message) {
		super(message);

		this.field = field;
	}

	@Override
	public JsonError.Error getError() {
		return ILLEGAL_PARAMETER.forField(field);
	}
}
