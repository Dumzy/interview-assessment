package com.zinkwork.Atm.service;

import com.zinkwork.Atm.constant.AtmValidationMessages;
import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.model.repository.AtmAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AtmAdminService {

    @Autowired
    private AtmAdminRepository atmAdminRepository;

    Logger logger = LoggerFactory.getLogger(AtmAdminService.class);

    public AtmAdmin initializeNotes(AtmAdmin atmAdmin) {

        try {

            logger.info("Add Bank Balance with Number of Notes");

            AtmAdmin atmAdminCurrent = getTopRecord();

            atmAdminCurrent.setAmount(atmAdmin.getAmount() + atmAdminCurrent.getAmount());
            atmAdminCurrent.setFiftyNotes(atmAdmin.getFiftyNotes() + atmAdminCurrent.getFiftyNotes());
            atmAdminCurrent.setTwentyNotes(atmAdmin.getTwentyNotes() + atmAdminCurrent.getTwentyNotes());
            atmAdminCurrent.setTenNotes(atmAdmin.getTenNotes() + atmAdminCurrent.getTenNotes());
            atmAdminCurrent.setFiveNotes(atmAdmin.getFiveNotes() + atmAdminCurrent.getFiveNotes());

            atmAdminRepository.save(atmAdminCurrent);

            return atmAdmin;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateNotes(AtmAdmin atmUpdatedNotes, AtmAdmin currentNotes) {

        try {
            logger.info("Update Existing Bank Balance with Number of Notes");

            AtmAdmin atmLatest = new AtmAdmin();

            atmLatest.setId(atmUpdatedNotes.getId());
            atmLatest.setAmount(atmUpdatedNotes.getAmount());
            atmLatest.setFiftyNotes(currentNotes.getFiftyNotes() - atmUpdatedNotes.getFiftyNotes());
            atmLatest.setTwentyNotes(currentNotes.getTwentyNotes() - atmUpdatedNotes.getTwentyNotes());
            atmLatest.setTenNotes(currentNotes.getTenNotes() - atmUpdatedNotes.getTenNotes());
            atmLatest.setFiveNotes(currentNotes.getFiveNotes() - atmUpdatedNotes.getFiveNotes());

            atmAdminRepository.save(atmLatest);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public AtmAdmin getInitializedNotes() {
        return getTopRecord();
    }

    private AtmAdmin getTopRecord() {

        Optional<AtmAdmin> atmAdmin = atmAdminRepository.findById(1);

        if (atmAdmin.isPresent()) {
            return atmAdmin.get();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AtmValidationMessages.INVALID_WITHDRAWAL_VALUE);
    }
}
