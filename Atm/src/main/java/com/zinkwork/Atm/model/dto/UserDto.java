package com.zinkwork.Atm.model.dto;

import com.zinkwork.Atm.constant.AtmValidationMessages;

import javax.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank(message = AtmValidationMessages.ACCOUNT_NO_REQUIRED)
    private String accountNumber;

    @NotBlank(message = AtmValidationMessages.PIN_NO_REQUIRED)
    private String pin;

    @NotBlank(message = AtmValidationMessages.WITHDRAWAL_AMOUNT_REQUIRED)
    private int withdrawalAmount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(int withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
