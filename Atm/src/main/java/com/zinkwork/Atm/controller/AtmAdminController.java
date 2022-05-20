package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.model.AtmAdmin;
import com.zinkwork.Atm.service.AtmAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name="atm-admin-controller")
@RestController
@RequestMapping("/atm/admin")
@Validated
@RequiredArgsConstructor
public class AtmAdminController {

    @Autowired
    private AtmAdminService atmAdminService;

    Logger logger = LoggerFactory.getLogger(AtmAdminController.class);

    @Operation(tags="atm-admin-controller",summary = "getATMNotes",description = "rest call to return the bank notes")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @GetMapping(path = "/getNotes",produces = APPLICATION_JSON_VALUE)
    public AtmAdmin getInitializedNotes() {

        logger.info("Retrieve Current Bank Balance with Number of Notes");

        return atmAdminService.getInitializedNotes();

    }

    @Operation(tags="atm-admin-controller",summary = "setATMNotes",description = "rest call to set bank notes")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @PostMapping(path = "/setNotes",produces = APPLICATION_JSON_VALUE)
    public AtmAdmin setATMAccountDetails(@RequestBody @Valid AtmAdmin atmAdmin) {

        logger.info("Set Bank Balance Amount with Number of Notes");

        return atmAdminService.initializeNotes(atmAdmin);
    }
}
