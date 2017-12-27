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

package com.shrralis.ssdemo1.controller.system;

import com.shrralis.ssdemo1.exception.BadParameterFormatException;
import com.shrralis.ssdemo1.exception.EntityNotUniqueException;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:45 PM
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNotUniqueException.class)
    public JsonResponse entityNotUniqueExceptionHandler(EntityNotUniqueException e) {
	    logger.error("EntityNotUniqueException: {}", e.getMessage());
	    return new JsonResponse(e.getError());
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadParameterFormatException.class)
	public JsonResponse badParameterFormatExceptionHandler(BadParameterFormatException e) {
		logger.error("BadParameterFormatException: {}", e.getMessage());
		return new JsonResponse(e.getError());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
	    logger.error("IllegalArgumentException: {}", e.getMessage());

	    Matcher matcher = Pattern.compile("\'([^\']*)\'").matcher(e.getMessage());
	    String msg = JsonError.Error.MISSING_PARAMETER.getMessage();

	    if (matcher.find()) {
		    msg = matcher.group(1);
	    }
	    return new JsonResponse(JsonError.Error.MISSING_PARAMETER.setParam(msg));
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public JsonResponse missingRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
		String msg = JsonError.Error.MISSING_PARAMETER.getMessage();

		logger.error("MissingServletRequestParameterException: {}", e.getMessage());

		if (e.getMessage() != null) {
			Matcher matcher = Pattern.compile("\'([^\']*)\'").matcher(e.getMessage());

			if (matcher.find()) {
				msg = matcher.group(1);
			}
		}
		return new JsonResponse(JsonError.Error.MISSING_PARAMETER.setParam(msg));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public JsonResponse generalExceptionHandler(Exception e) {
	    logger.error("Exception: {}", e.getMessage());
	    return new JsonResponse(e.getMessage());
    }
}
