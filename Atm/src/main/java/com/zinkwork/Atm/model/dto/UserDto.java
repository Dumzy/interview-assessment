package com.zinkwork.Atm.model.dto;

public class UserDto {

    private String accountNumber;
    private String pin;
    private Integer withdrawalAmount;

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

    public Integer getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Integer withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
