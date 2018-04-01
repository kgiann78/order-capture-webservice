package com.constantine.ordercapture.service;


import com.constantine.ordercapture.exception.ResourceNotFoundException;
import com.constantine.ordercapture.exception.ServerError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorResponseController {
    private Logger logger = Logger.getLogger(ErrorResponseController.class);


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    ServerError handleBadRequest(HttpServletRequest req, Exception ex) {
        logger.warn(ex);
        return new ServerError(req.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ServerError handleServerErrors(HttpServletRequest req, Exception ex) {
        logger.fatal(ex);
        return new ServerError(req.getRequestURL().toString(), ex);
    }
}
