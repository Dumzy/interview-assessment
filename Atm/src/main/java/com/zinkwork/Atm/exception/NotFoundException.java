package com.zinkwork.Atm.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, ExceptionMessages.NOT_FOUND);
    }

    public NotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public NotFoundException(HttpStatus httpStatus, String message, Exception e) {
        super(httpStatus, message, e);
    }
}
