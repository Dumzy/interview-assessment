package com.zinkwork.Atm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.model.AtmAdmin;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ExtendWith(MockitoExtension.class)
public class AtmAdminControllerTest {

    @Mock
    private AtmAdminService atmAdminService;

    @Mock
    private AtmAdminRepository atmAdminRepository;

    @InjectMocks
    private AtmAdminController atmAdminController;

    private static AtmAdmin atmAdmin;

    @BeforeAll
    public static void setup() throws IOException {
        File resource = new ClassPathResource("bank_notes.json").getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        atmAdmin = new ObjectMapper().readValue(content, AtmAdmin.class);
    }

    @Test
    public void testGetInitializedNotes() {

        Mockito.when(atmAdminService.getInitializedNotes()).thenReturn(atmAdmin);
        AtmAdmin result = atmAdminController.getInitializedNotes();

        Assertions.assertNotNull(result);
        Mockito.verify(atmAdminService, Mockito.times(1)).getInitializedNotes();
    }

    @Test
    public void testSetATMAccountDetails() {

        Mockito.when(atmAdminService.initializeNotes(Mockito.any())).thenReturn(atmAdmin);
        Mockito.lenient().when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
        AtmAdmin result = atmAdminController.setATMAccountDetails(atmAdmin);

        Assertions.assertNotNull(result);
        Mockito.verify(atmAdminService, Mockito.times(1)).initializeNotes(Mockito.any());
    }

}
