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

import com.shrralis.ssdemo1.exception.EntityNotUniqueException;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:45 PM
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNotUniqueException.class)
    public JsonResponse entityNotUniqueHandler(EntityNotUniqueException e) {
//        logger.error("EntityNotUniqueException: {}", e.getMessage());
        return new JsonResponse(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String illegalArgumentException(IllegalArgumentException e) {
//        logger.error("IllegalArgumentException: {}", e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String generalExceptionHandler(Exception e) {
//        logger.error("Exception: {}", e.getMessage());
        return e.getMessage();
    }
}
