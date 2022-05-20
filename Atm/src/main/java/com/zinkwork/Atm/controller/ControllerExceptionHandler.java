package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.exception.BaseException;
import com.zinkwork.Atm.exception.BaseExceptionDto;
import com.zinkwork.Atm.exception.ExceptionMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<String> handleException(BaseException e, HttpServletRequest request, HttpServletResponse response) {

        logger.error("Exception Trace", e);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto(e);
        return new ResponseEntity<>(baseExceptionDto.toString(), responseHeaders, e.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {

        logger.error("Exception Trace", e);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto(httpStatus.value(), ExceptionMessages.INTERNAL);
        return new ResponseEntity<>(baseExceptionDto.toString(), responseHeaders, httpStatus);
    }
}
