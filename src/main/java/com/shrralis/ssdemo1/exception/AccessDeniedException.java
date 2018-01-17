package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

public class AccessDeniedException extends AbstractCitizenException {

    @Override
    public JsonError.Error getError() {
        return JsonError.Error.ACCESS_DENIED;
    }
}
