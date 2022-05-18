package com.zinkwork.Atm.service;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.model.Account;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.dto.UserDto;
import com.zinkwork.Atm.model.repository.AccountRepository;
import com.zinkwork.Atm.util.AccountBalanceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AtmService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountBalanceValidation accountBalanceValidation;

    public AccountDto getAccountDetails(UserDto userDto) {

        Account account = getByAccountNumAndPin(userDto.getAccountNumber(), userDto.getPin());

        return new AccountDto(account.getBalance(), account.getBalance() + account.getOverdraft());
    }

    public synchronized AccountDto cashWithdrawal(UserDto userDto) {

        Account account = getByAccountNumAndPin(userDto.getAccountNumber(), userDto.getPin());

        AccountDto accountDto = accountBalanceValidation.checkAccountBalance(userDto.getWithdrawalAmount(),
                account.getBalance(), account.getBalance() + account.getOverdraft());

        account.setBalance(accountDto.getBalanceAmount());
        account.setOverdraft(accountDto.getMaxWithdrawalAmount() - accountDto.getBalanceAmount());

        accountRepository.save(account);

        return accountBalanceValidation.checkAccountBalance(userDto.getWithdrawalAmount(), account.getBalance(),
                account.getBalance() + account.getOverdraft());

    }

    public Account getByAccountNumAndPin(String accNo, String pinNo) {

        Optional<Account> account = accountRepository.findByAccountnumberAndPin(accNo, pinNo);

        if (!account.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, AtmValidationMessages.INVALID_ACCOUNT);
        }

        return account.get();
    }
}
