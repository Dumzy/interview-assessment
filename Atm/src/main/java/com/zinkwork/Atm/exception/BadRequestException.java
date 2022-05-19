package com.zinkwork.Atm.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Exception e) {
        super(HttpStatus.BAD_REQUEST, message, e);
    }
}
