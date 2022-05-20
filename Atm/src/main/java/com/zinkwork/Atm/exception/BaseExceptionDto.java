package com.zinkwork.Atm.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zinkwork.Atm.util.ConversionUtils;

public class BaseExceptionDto {

    @JsonProperty("code")
    private int code;

    @JsonProperty("description")
    private String description;

    public BaseExceptionDto(BaseException e) {
        this.code = e.getHttpStatus().value();
        this.description = e.getMessage();
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

    @Override
    public String toString() {
        return ConversionUtils.generateJSON(this);
    }
}
