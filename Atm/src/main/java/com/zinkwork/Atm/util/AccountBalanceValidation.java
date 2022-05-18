package com.zinkwork.Atm.util;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.model.dto.AccountDto;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Component
public class AccountBalanceValidation {

    public AccountDto checkAccountBalance(int withdrawalAmount, int accBalance, int overdraft) {

        int totalBalance = accBalance + overdraft;

        if (withdrawalAmount > totalBalance) {
//            return null;
            throw new ResponseStatusException(BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BALANCE);
        } else {
            if (withdrawalAmount < accBalance) {
                int balance = accBalance - withdrawalAmount;
                return new AccountDto(balance, balance + overdraft, divideBalance(balance));
            } else {
                int balance = totalBalance - withdrawalAmount;
                return new AccountDto(0, balance, divideBalance(balance));
            }
        }
    }

    private String divideBalance(int cashValue) {

        int[] notes = new int[] { 50, 20, 10, 5 };
        int[] noteCounter = new int[4];
        StringBuilder currencyCount = new StringBuilder();

        if (cashValue % 5 != 0) {
            throw new ResponseStatusException(BAD_REQUEST, AtmValidationMessages.INVALID_WITHDRAWAL_VALUE);
        }

        for (int i = 0; i < notes.length; i++) {
            if (cashValue >= notes[i]) {
                noteCounter[i] = cashValue / notes[i];
                cashValue = cashValue - noteCounter[i] * notes[i];
            }
        }

        for (int i = 0; i < notes.length; i++) {
            if (noteCounter[i] != 0) {
                currencyCount = currencyCount.append("€"+notes[i] + " x " + noteCounter[i] + " ");
            }
        }

        return currencyCount.toString();
    }
}
