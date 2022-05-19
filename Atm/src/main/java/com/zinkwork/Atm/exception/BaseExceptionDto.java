package com.zinkwork.Atm.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseExceptionDto {

    @JsonProperty("code")
    private int code;

    @JsonProperty("description")
    private String description;

    public BaseExceptionDto(BaseException e) {
        this.code = e.getHttpStatus().value();
        this.description = description;
    }

    public BaseExceptionDto(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
