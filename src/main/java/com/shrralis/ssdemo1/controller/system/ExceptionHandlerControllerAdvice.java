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

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.exception.EntityNotUniqueException;
import com.shrralis.ssdemo1.exception.ExpiredRecoveryTokenException;
import com.shrralis.ssdemo1.exception.IllegalParameterException;
import com.shrralis.ssdemo1.security.exception.CitizenBadCredentialsException;
import com.shrralis.ssdemo1.security.exception.EmailNotFoundException;
import com.shrralis.ssdemo1.security.exception.TooManyNonExpiredRecoveryTokensException;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:45 PM
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	private Function<FieldError, JsonError> mapFieldError = fieldError ->
			new JsonError(JsonError.Error.valueOf(fieldError.getDefaultMessage()).forField(fieldError.getField()));

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = TooManyNonExpiredRecoveryTokensException.class)
	public JsonResponse tooManyNonExpiredRecoveryTokensException(TooManyNonExpiredRecoveryTokensException e) {
		logger.error("TooManyNonExpiredRecoveryTokensException: {}", e);
		return new JsonResponse(JsonError.Error.TOO_MANY_NON_EXPIRED_RECOVERY_TOKENS);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = IllegalParameterException.class)
	public JsonResponse illegalParameterException(IllegalParameterException e) {
		logger.error("IllegalParameterException: {}", e);
		return new JsonResponse(e.getError());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ExpiredRecoveryTokenException.class)
	public JsonResponse expiredRecoveryTokenException(ExpiredRecoveryTokenException e) {
		logger.error("BadCredentialsException: {}", e);
		return new JsonResponse(e.getError());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadCredentialsException.class)
	public JsonResponse badCredentialsException(BadCredentialsException e) {
		logger.error("BadCredentialsException: {}", e);
		return new JsonResponse(JsonError.Error.BAD_CREDENTIALS);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = CitizenBadCredentialsException.class)
	public JsonResponse citizenBadCredentialsException(CitizenBadCredentialsException e) {
		logger.error("CitizenBadCredentialsException: {}", e);
		return new JsonResponse(JsonError.Error.BAD_CREDENTIALS
				.forField(e.getFailedAttempts() + "/" + User.MAX_FAILED_AUTH_VALUE));
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = EmailNotFoundException.class)
	public JsonResponse emailNotFoundException(EmailNotFoundException e) {
		logger.error("EmailNotFoundException: {}", e);
		return new JsonResponse(JsonError.Error.USER_NOT_EXIST.forField("email"));
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public JsonResponse usernameNotFoundException(UsernameNotFoundException e) {
		logger.error("UsernameNotFoundException: {}", e);
		return new JsonResponse(JsonError.Error.USER_NOT_EXIST.forField("login"));
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public JsonResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		logger.error("MethodArgumentNotValidException: {}", e);
		return new JsonResponse(
				e.getBindingResult().getFieldErrors().stream()
						.map(mapFieldError)
						.distinct()
						.collect(Collectors.toList()));
	}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNotUniqueException.class)
    public JsonResponse entityNotUniqueExceptionHandler(EntityNotUniqueException e) {
	    logger.error("EntityNotUniqueException: {}", e);
	    return new JsonResponse(e.getError());
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadFieldFormatException.class)
	public JsonResponse badParameterFormatExceptionHandler(BadFieldFormatException e) {
		logger.error("BadFieldFormatException: {}", e);
		return new JsonResponse(e.getError());
    }

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public JsonResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
		logger.error("IllegalArgumentException: {}", e);
		return new JsonResponse(new JsonError("Internal error"));
    }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public JsonResponse missingRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
		logger.error("MissingServletRequestParameterException: {}", e);
		return new JsonResponse(JsonError.Error.MISSING_FIELD.forField(e.getParameterName()));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public JsonResponse generalExceptionHandler(Exception e) {
	    logger.error("{}: {}", e.getClass(), e);
	    return new JsonResponse(new JsonError(e.getMessage()));
    }
}
