package com.zinkwork.Atm.exception;

import org.springframework.http.HttpStatus;

public class InternalErrorException extends BaseException{

    public InternalErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessages.INTERNAL);
    }

    public InternalErrorException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public InternalErrorException(HttpStatus httpStatus, String message, Exception e) {
        super(httpStatus, message, e);
    }

    public InternalErrorException(String message, Exception e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, e);
    }
}
