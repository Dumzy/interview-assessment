package com.zinkwork.Atm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.repository.AtmAdminRepository;
import com.zinkwork.Atm.service.AtmAdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ExtendWith(MockitoExtension.class)
public class AccountBalanceValidationTest {

    @Mock
    private AtmAdminRepository atmAdminRepository;

    @Mock
    private AtmAdminService atmAdminService;

    @InjectMocks
    private AccountBalanceValidation accountBalanceValidation;

    private static AccountDto accountDto;
    private static AtmAdmin atmAdmin;

    @BeforeAll
    public static void setup() throws IOException {
        File resource = new ClassPathResource("user_account.json").getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        accountDto = new ObjectMapper().readValue(content, AccountDto.class);

        File atmNotesResource = new ClassPathResource("atm_notes.json").getFile();
        String atmContent = new String(Files.readAllBytes(atmNotesResource.toPath()));
        atmAdmin = new ObjectMapper().readValue(atmContent, AtmAdmin.class);
    }

    @Test
    public void testCheckAccountBalanceWhenWithdrawalAmountLess() {

        Mockito.lenient().when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
        AccountDto accountDtos = accountBalanceValidation.checkAccountBalance(500, 800, 100, atmAdmin);

        Assertions.assertNotNull(accountDtos);
        Assertions.assertEquals(300, accountDtos.getBalanceAmount());
        Assertions.assertEquals(400, accountDtos.getMaxWithdrawalAmount());
    }

    @Test
    public void testCheckAccountBalanceWhenWithdrawalAmountGreater() {

        Mockito.lenient().when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
        AccountDto accountDtos = accountBalanceValidation.checkAccountBalance(700, 600, 100, atmAdmin);

        Assertions.assertNotNull(accountDtos);
        Assertions.assertEquals(0, accountDtos.getBalanceAmount());
        Assertions.assertEquals(0, accountDtos.getMaxWithdrawalAmount());
    }

    @Test
    public void testCheckAccountBalanceWhenWithdrawalAmountGreaterThanBank() {

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            Mockito.lenient().when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
            AccountDto accountDtos = accountBalanceValidation.checkAccountBalance(2000, 600, 100, atmAdmin);
        });
    }

    @Test
    public void testCheckAccountBalanceWhenWithdrawalAmountGreaterThanUserAccount() {

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            Mockito.lenient().when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
            AccountDto accountDtos = accountBalanceValidation.checkAccountBalance(1000, 600, 100, atmAdmin);
        });
    }
}
