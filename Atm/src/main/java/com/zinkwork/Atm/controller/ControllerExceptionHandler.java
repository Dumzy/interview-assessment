package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.exception.BaseException;
import com.zinkwork.Atm.exception.BaseExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<String> handleException(BaseException e, HttpServletRequest request,
                                                  HttpServletResponse response) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto(e);
        return new ResponseEntity<>(baseExceptionDto.toString(), responseHeaders, e.getHttpStatus());
    }
}
