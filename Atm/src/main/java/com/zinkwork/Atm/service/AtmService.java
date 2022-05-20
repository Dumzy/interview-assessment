package com.zinkwork.Atm.service;

import com.zinkwork.Atm.exception.BadRequestException;
import com.zinkwork.Atm.exception.InternalErrorException;
import com.zinkwork.Atm.exception.NotFoundException;
import com.zinkwork.Atm.model.Account;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.dto.UserDto;
import com.zinkwork.Atm.model.repository.AccountRepository;
import com.zinkwork.Atm.util.AccountBalanceValidation;
import com.zinkwork.Atm.validation.CommonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtmService {

    Logger logger = LoggerFactory.getLogger(AtmService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountBalanceValidation accountBalanceValidation;

    @Autowired
    private AtmAdminService atmAdminService;

    public AccountDto getAccountDetails(UserDto userDto) {

        logger.info("Inside getAccountDetails method");

        CommonValidator.validateUser(userDto);

        try {

            Account account = getByAccountNumAndPin(userDto.getAccountNumber(), userDto.getPin());
            return new AccountDto(account.getBalance(), account.getBalance() + account.getOverdraft());

        } catch (NotFoundException e) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new InternalErrorException();
        }
    }

    public synchronized AccountDto cashWithdrawal(UserDto userDto) {

        logger.info("Inside cashWithdrawal method");

        CommonValidator.validateUserCashWithdrawal(userDto);

        try {

            Account account = getByAccountNumAndPin(userDto.getAccountNumber(), userDto.getPin());
            AccountDto accountDto = accountBalanceValidation.checkAccountBalance(userDto.getWithdrawalAmount(),
                    account.getBalance(), account.getOverdraft(), atmAdminService.getInitializedNotes());

            account.setBalance(accountDto.getBalanceAmount());
            account.setOverdraft(accountDto.getMaxWithdrawalAmount() - accountDto.getBalanceAmount());

            accountRepository.save(account);

            return accountDto;

        } catch (NotFoundException e) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalErrorException();
        }

    }

    public Account getByAccountNumAndPin(String accNo, String pinNo) {

        Optional<Account> account = accountRepository.findByAccountnumberAndPin(accNo, pinNo);

        if (!account.isPresent()) {
            throw new NotFoundException();
        }

        return account.get();
    }
}
