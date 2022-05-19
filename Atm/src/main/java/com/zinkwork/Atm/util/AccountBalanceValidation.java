package com.zinkwork.Atm.util;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.service.AtmAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountBalanceValidation {

    @Autowired
    private AtmAdminService atmAdminService;

    Logger logger = LoggerFactory.getLogger(AccountBalanceValidation.class);

    public AccountDto checkAccountBalance(int withdrawalAmount, int accBalance, int overdraft, AtmAdmin atmCurrentNotes) {

        logger.info("Inside checkAccountBalance Method");

        int totalBalance = accBalance + overdraft;

        if (withdrawalAmount > atmCurrentNotes.getAmount()) {
            logger.error(AtmValidationMessages.NO_ENOUGH_BANK_BALANCE);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BANK_BALANCE);

        } else if (withdrawalAmount > totalBalance) {
            logger.error(AtmValidationMessages.NO_ENOUGH_BALANCE);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BALANCE);

        } else {

            if (withdrawalAmount <= accBalance) {

                int balance = accBalance - withdrawalAmount;
                AtmAdmin notesNeeded = checkNumberOfNotesNeeded(withdrawalAmount, atmCurrentNotes);
                return new AccountDto(balance, balance + overdraft, setMessage(notesNeeded));

//                AtmAdmin atmNotesNeeded = splitBalanceintoNotes(withdrawalAmount, atmCurrentNotes);
//
//                if (atmNotesNeeded == null) {
//                    logger.error(AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
//                }
//
//                atmAdminService.updateNotes(atmNotesNeeded, atmCurrentNotes);
//                return new AccountDto(balance, balance + overdraft, setMessage(atmNotesNeeded));

            } else {

                int balance = totalBalance - withdrawalAmount;
                AtmAdmin notesNeeded = checkNumberOfNotesNeeded(withdrawalAmount, atmCurrentNotes);
                return new AccountDto(0, balance, setMessage(notesNeeded));

//                AtmAdmin atmNotesNeeded = splitBalanceintoNotes(withdrawalAmount, atmCurrentNotes);
//
//                if (atmNotesNeeded == null) {
//                    logger.error(AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
//                }
//
//                atmAdminService.updateNotes(atmNotesNeeded, atmCurrentNotes);
//                return new AccountDto(0, balance, setMessage(atmNotesNeeded));
            }
        }
    }

    private AtmAdmin checkNumberOfNotesNeeded(int withAmount, AtmAdmin currentATMNotes) {

        logger.info("Inside checkNumberOfNotesNeeded Method");

        AtmAdmin atmNotesNeeded = splitBalanceintoNotes(withAmount, currentATMNotes);

        if (atmNotesNeeded == null) {
            logger.error(AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.NO_ENOUGH_BANK_NOTES);
        }

        atmAdminService.updateNotes(atmNotesNeeded, currentATMNotes);

        return atmNotesNeeded;
    }

    private AtmAdmin splitBalanceintoNotes(int cashAmount, AtmAdmin currentNotes) {

        logger.info("Inside splitBalanceintoNotes Method");

        int[] notes = new int[] { 50, 20, 10, 5 };
        int[] noteCounter = new int[4];
        int cashValue = cashAmount;

        if (cashValue % 5 != 0) {
            logger.error(AtmValidationMessages.INVALID_WITHDRAWAL_VALUE);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.INVALID_WITHDRAWAL_VALUE);
        }

        AtmAdmin atmAdmin = new AtmAdmin();

        for (int i = 0; i < notes.length; i++) {

            if (cashValue >= notes[i]) {

                noteCounter[i] = cashValue / notes[i];

                if (notes[i] == 50) {

                    if (noteCounter[i] <= currentNotes.getFiftyNotes()) {
                        atmAdmin.setFiftyNotes(noteCounter[i]);
                        cashValue = cashValue - noteCounter[i] * notes[i];
                    } else {
                        atmAdmin.setFiftyNotes(currentNotes.getFiftyNotes());
                        cashValue = cashValue - currentNotes.getFiftyNotes() * notes[i];
                    }
                } else if (notes[i] == 20) {

                    if (noteCounter[i] <= currentNotes.getTwentyNotes()) {
                        atmAdmin.setTwentyNotes(noteCounter[i]);
                        cashValue = cashValue - noteCounter[i] * notes[i];
                    } else {
                        atmAdmin.setFiftyNotes(currentNotes.getTwentyNotes());
                        cashValue = cashValue - currentNotes.getTwentyNotes() * notes[i];
                    }
                } else if (notes[i] == 10) {

                    if (noteCounter[i] <= currentNotes.getTenNotes()) {
                        atmAdmin.setTenNotes(noteCounter[i]);
                        cashValue = cashValue - noteCounter[i] * notes[i];
                    } else {
                        atmAdmin.setFiftyNotes(currentNotes.getTenNotes());
                        cashValue = cashValue - currentNotes.getTenNotes() * notes[i];
                    }
                } else if (notes[i] == 5) {

                    if (noteCounter[i] <= currentNotes.getFiveNotes()) {
                        atmAdmin.setFiveNotes(noteCounter[i]);
                        cashValue = cashValue - noteCounter[i] * notes[i];
                    } else {
                        atmAdmin.setFiftyNotes(currentNotes.getFiftyNotes());
                        cashValue = cashValue - currentNotes.getFiveNotes() * notes[i];
                    }
                }
            }
        }

        if (cashValue == 0) {
            atmAdmin.setId(currentNotes.getId());
            atmAdmin.setAmount(currentNotes.getAmount() - cashAmount);

            return atmAdmin;
        }

        return null;
    }

    private String setMessage(AtmAdmin atmNotes) {

        String message = "€50 X " + atmNotes.getFiftyNotes() + ", €20 X " + atmNotes.getTwentyNotes() +
                ", €10 X " + atmNotes.getTenNotes() + ", €5 X " + atmNotes.getFiveNotes();

        return message;
    }
}
