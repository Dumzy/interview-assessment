package com.zinkwork.Atm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.repository.AtmAdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AtmAdminServiceTest {

    @Mock
    private AtmAdminRepository atmAdminRepository;

    @InjectMocks
    private AtmAdminService atmAdminService;

    private static AtmAdmin atmAdmin;

    @BeforeAll
    public static void setup() throws IOException {
        File resource = new ClassPathResource("bank_notes.json").getFile();
        String content = new String(Files.readAllBytes(resource.toPath()));
        atmAdmin = new ObjectMapper().readValue(content, AtmAdmin.class);
    }

    @Test
    public void testInitializeNotes() {

        Mockito.when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(atmAdmin));
        Mockito.when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
        AtmAdmin atmAdmins = atmAdminService.initializeNotes(atmAdmin);

        Assertions.assertNotNull(atmAdmins);
        Mockito.verify(atmAdminRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testInitializeNotesException() {

        Assertions.assertThrows(Exception.class, () -> {
            Mockito.when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(atmAdmin));
            Mockito.when(atmAdminRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
            atmAdminService.initializeNotes(atmAdmin);
        });
    }

    @Test
    public void testUpdateNotes() {

        AtmAdmin atmCurrent = atmAdmin;
        atmCurrent.setFiftyNotes(50);
        atmCurrent.setTwentyNotes(50);
        atmCurrent.setTenNotes(50);

        Mockito.lenient().when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(atmAdmin));
        Mockito.when(atmAdminRepository.save(Mockito.any())).thenReturn(atmAdmin);
        atmAdminService.updateNotes(atmAdmin, atmCurrent);

        Mockito.verify(atmAdminRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testUpdateNotesException() {

        Assertions.assertThrows(Exception.class, () -> {
            AtmAdmin atmCurrent = atmAdmin;
            atmCurrent.setFiftyNotes(50);
            atmCurrent.setTwentyNotes(50);
            atmCurrent.setTenNotes(50);

            Mockito.lenient().when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(atmAdmin));
            Mockito.when(atmAdminRepository.save(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
            atmAdminService.updateNotes(atmAdmin, atmCurrent);
        });
    }

    @Test
    public void testGetInitializeNotes() {

        Mockito.when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(atmAdmin));
        AtmAdmin atmAdmins = atmAdminService.getInitializedNotes();

        Assertions.assertNotNull(atmAdmins);
        Mockito.verify(atmAdminRepository, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    public void testGetInitializeNotesException() {

        Assertions.assertThrows(Exception.class, () -> {
            Mockito.when(atmAdminRepository.findById(Mockito.anyInt())).thenReturn(null);
            atmAdminService.getInitializedNotes();
        });
    }
}
