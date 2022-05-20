package com.zinkwork.Atm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.exception.NotFoundException;
import com.zinkwork.Atm.model.Account;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.dto.UserDto;
import com.zinkwork.Atm.model.repository.AccountRepository;
import com.zinkwork.Atm.util.AccountBalanceValidation;
import com.zinkwork.Atm.validation.CommonValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AtmServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceValidation accountBalanceValidation;

    @Mock
    private AtmAdminService atmAdminService;

    @InjectMocks
    private AtmService atmService;

    private static AccountDto accountDto;
    private static UserDto userDto;
    private static Account account;
    private static AtmAdmin atmAdmin;

    @BeforeAll
    public static void setup() throws IOException {
        File resource = new ClassPathResource("user_account.json").getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        accountDto = new ObjectMapper().readValue(content, AccountDto.class);

        File userResource = new ClassPathResource("user_dto.json").getFile();
        String userContent = new String(Files.readAllBytes(userResource.toPath()));
        userDto = new ObjectMapper().readValue(userContent, UserDto.class);

        File accResource = new ClassPathResource("account_detail.json").getFile();
        String accContent = new String(Files.readAllBytes(accResource.toPath()));
        account = new ObjectMapper().readValue(accContent, Account.class);

        File atmNotesResource = new ClassPathResource("atm_notes.json").getFile();
        String atmContent = new String(Files.readAllBytes(atmNotesResource.toPath()));
        atmAdmin = new ObjectMapper().readValue(atmContent, AtmAdmin.class);
    }

    @Test
    public void testGetAccountDetails() {

        Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(account));

        AccountDto accountDtos = atmService.getAccountDetails(userDto);
        Assertions.assertNotNull(accountDtos);

        Mockito.verify(accountRepository, Mockito.times(1)).findByAccountnumberAndPin(Mockito.anyString(),
                Mockito.anyString());
    }

    @Test
    public void testGetAccountDetailsNotFoundException() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString()))
                    .thenThrow(new NotFoundException());

            atmService.getAccountDetails(userDto);
        });

    }

    @Test
    public void testGetAccountDetailsException() {

        Assertions.assertThrows(Exception.class, () -> {

            Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString()))
                    .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

            atmService.getAccountDetails(userDto);
        });

    }

    @Test
    public void testCashWithdrawal() {

        Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString())).
                thenReturn(Optional.of(account));
        Mockito.lenient().when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(account));
        Mockito.when(accountBalanceValidation.checkAccountBalance(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.any())).thenReturn(accountDto);

        AccountDto accountDtos = atmService.cashWithdrawal(userDto);
        Assertions.assertNotNull(accountDtos);

        Mockito.verify(accountRepository, Mockito.times(1)).findByAccountnumberAndPin(Mockito.anyString(),
                Mockito.anyString());
        Mockito.verify(accountBalanceValidation, Mockito.times(1)).checkAccountBalance(Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.any());
    }

    @Test
    public void testCashWithdrawalNotFoundException() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString()))
                    .thenThrow(new NotFoundException());
            atmService.cashWithdrawal(userDto);
        });
    }

    @Test
    public void testCashWithdrawalException() {

        Assertions.assertThrows(Exception.class, () -> {

            Mockito.when(accountRepository.findByAccountnumberAndPin(Mockito.anyString(), Mockito.anyString())).
                    thenReturn(Optional.of(account));
            Mockito.lenient().when(accountRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(account));
            Mockito.when(accountBalanceValidation.checkAccountBalance(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(),
                    Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

            atmService.cashWithdrawal(userDto);
        });
    }

}
