package com.zinkwork.Atm.validation;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class CommonValidator {

    private CommonValidator() {
    }

    public static void validateUser(UserDto userDto) {

        if (userDto.getAccountNumber() == null || "".equals(userDto.getAccountNumber().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.ACCOUNT_NO_REQUIRED);
        } else if (userDto.getPin() == null || "".equals(userDto.getPin().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.PIN_NO_REQUIRED);
        } else if (String.valueOf(userDto.getWithdrawalAmount()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.WITHDRAWAL_AMOUNT_REQUIRED);
        }
    }
}
