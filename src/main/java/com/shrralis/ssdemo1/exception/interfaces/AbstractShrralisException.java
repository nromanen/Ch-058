package com.shrralis.ssdemo1.exception.interfaces;

import com.shrralis.tools.model.JsonError;

/**
 * Interface of exceptions that can give us some {@link com.shrralis.tools.model.JsonError.Error}
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/24/17 at 2:19 AM
 */
public abstract class AbstractShrralisException extends Exception {
	public AbstractShrralisException() {
		super();
	}

	public AbstractShrralisException(String message) {
		super(message);
	}

	public AbstractShrralisException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractShrralisException(Throwable cause) {
		super(cause);
	}

	protected AbstractShrralisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public abstract JsonError.Error getError();
}
