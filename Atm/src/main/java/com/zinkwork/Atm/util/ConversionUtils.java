package com.zinkwork.Atm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.exception.InternalErrorException;

public class ConversionUtils {

    private ConversionUtils() {
    }

    public static String generateJSON(Object object) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);

        } catch (Exception e) {
            throw new InternalErrorException(e.getMessage(), e);
        }
    }
}
