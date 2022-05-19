package com.zinkwork.Atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.dto.UserDto;
import com.zinkwork.Atm.service.AtmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ExtendWith(MockitoExtension.class)
public class AtmControllerTest {

    @Mock
    private AtmService atmservice;

    @InjectMocks
    private AtmController atmController;

    private static AccountDto accountDto;
    private static UserDto userDto;

    @BeforeAll
    public static void setup() throws IOException {

        File resource = new ClassPathResource("user_account.json").getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        accountDto = new ObjectMapper().readValue(content, AccountDto.class);

        File userResource = new ClassPathResource("user_dto.json").getFile();
        String userContent = new String(Files.readAllBytes(userResource.toPath()));
        userDto = new ObjectMapper().readValue(userContent, UserDto.class);
    }

    @Test
    public void testGetAccount() {

        Mockito.when(atmservice.getAccountDetails(Mockito.any())).thenReturn(accountDto);
        AccountDto result = atmController.getAccount(userDto);

        Assertions.assertNotNull(result);
        Mockito.verify(atmservice, Mockito.times(1)).getAccountDetails(Mockito.any());
    }

    @Test
    public void testCashWithdrawal() {

        Mockito.when(atmservice.cashWithdrawal(Mockito.any())).thenReturn(accountDto);
        AccountDto result = atmController.cashWithdrawal(userDto);

        Assertions.assertNotNull(result);
        Mockito.verify(atmservice, Mockito.times(1)).cashWithdrawal(Mockito.any());
    }
}
