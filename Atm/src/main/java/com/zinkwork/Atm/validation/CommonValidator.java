package com.zinkwork.Atm.validation;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.exception.BadRequestException;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.dto.UserDto;

public final class CommonValidator {

    private CommonValidator() {
    }

    public static void validateUser(UserDto userDto) {

        if (userDto.getAccountNumber() == null || "".equals(userDto.getAccountNumber().trim())) {
            throw new BadRequestException(AtmValidationMessages.ACCOUNT_NO_REQUIRED);
        }

        if (userDto.getPin() == null || "".equals(userDto.getPin().trim())) {
            throw new BadRequestException(AtmValidationMessages.PIN_NO_REQUIRED);
        }
    }

    public static void validateUserCashWithdrawal(UserDto userDto) {

        validateUser(userDto);

        if (userDto.getWithdrawalAmount() == null || userDto.getWithdrawalAmount() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_WITHDRAWAL_AMOUNT);
        }
    }

    public static void validateATMAccount(AtmAdmin atmAdmin) {

        if (atmAdmin.getAmount() == null || atmAdmin.getAmount() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_ATM_AMOUNT);
        }

        if (atmAdmin.getFiftyNotes() == null || atmAdmin.getFiftyNotes() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_FIFTY_NOTES);
        }

        if (atmAdmin.getTwentyNotes() == null || atmAdmin.getTwentyNotes() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_TWENTY_NOTES);
        }

        if (atmAdmin.getTenNotes() == null || atmAdmin.getTenNotes() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_TEN_NOTES);
        }

        if (atmAdmin.getFiveNotes() == null || atmAdmin.getFiveNotes() < 0) {
            throw new BadRequestException(AtmValidationMessages.INVALID_FIVE_NOTES);
        }
    }
}
