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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.exception.EntityNotUniqueException;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:45 PM
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadCredentialsException.class)
	public JsonResponse badCredentialsException(BadCredentialsException e) {
		logger.error("BadCredentialsException: {}", e.getMessage());
		return new JsonResponse(JsonError.Error.BAD_CREDENTIALS);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public JsonResponse usernameNotFoundException(UsernameNotFoundException e) {
		logger.error("UsernameNotFoundException: {}", e.getMessage());
		return new JsonResponse(JsonError.Error.USER_NOT_EXIST);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public JsonResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		logger.error("MethodArgumentNotValidException: {}", e.getMessage());
		return new JsonResponse(Lists.newArrayList(Sets.newHashSet(e.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> new JsonError(JsonError.Error.valueOf(fieldError.getDefaultMessage())
						.forField(fieldError.getField()))).collect(Collectors.toList()))));
	}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNotUniqueException.class)
    public JsonResponse entityNotUniqueExceptionHandler(EntityNotUniqueException e) {
	    logger.error("EntityNotUniqueException: {}", e.getMessage());
	    return new JsonResponse(e.getError());
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadFieldFormatException.class)
	public JsonResponse badParameterFormatExceptionHandler(BadFieldFormatException e) {
		logger.error("BadFieldFormatException: {}", e.getMessage());
		return new JsonResponse(e.getError());
    }

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
	    logger.error("IllegalArgumentException: {}", e.getMessage());
		return new JsonResponse(new JsonError("Internal error"));
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public JsonResponse missingRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
		return new JsonResponse(JsonError.Error.MISSING_FIELD.forField(e.getParameterName()));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public JsonResponse generalExceptionHandler(Exception e) {
	    logger.error("{}: {}", e.getClass(), e.getMessage());
	    return new JsonResponse(new JsonError(e.getMessage()));
    }
}
