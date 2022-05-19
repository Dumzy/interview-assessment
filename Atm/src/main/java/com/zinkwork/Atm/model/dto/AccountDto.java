package com.zinkwork.Atm.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountDto {

    private int balanceAmount;
    private int maxWithdrawalAmount;
    private String message = null;

    public AccountDto() {
    }

    public AccountDto(int balanceAmount, int maxWithdrawalAmount) {
        this.balanceAmount = balanceAmount;
        this.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public AccountDto(int balanceAmount, int maxWithdrawalAmount, String message) {
        this.balanceAmount = balanceAmount;
        this.maxWithdrawalAmount = maxWithdrawalAmount;
        this.message = message;
    }

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(int balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public int getMaxWithdrawalAmount() {
        return maxWithdrawalAmount;
    }

    public void setMaxWithdrawalAmount(int maxWithdrawalAmount) {
        this.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
