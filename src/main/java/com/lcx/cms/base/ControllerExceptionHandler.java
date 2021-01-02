package com.lcx.cms.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    public Response handleAppException(AppException e) {
        return new Response(e.getCode(), e.getTips(), null);
    }
}
