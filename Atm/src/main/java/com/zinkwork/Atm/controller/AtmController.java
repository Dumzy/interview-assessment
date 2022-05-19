package com.zinkwork.Atm.controller;

import com.zinkwork.Atm.model.dto.AccountDto;
import com.zinkwork.Atm.model.dto.UserDto;
import com.zinkwork.Atm.service.AtmService;
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

@Tag(name="atm-rest-controller")
@RestController
@RequestMapping("/atm")
@Validated
@RequiredArgsConstructor
public class AtmController {

    @Autowired
    private AtmService atmservice;

    Logger logger = LoggerFactory.getLogger(AtmController.class);

    @Operation(tags="atm-rest-controller",summary = "getBalance",description = "rest call to return the balance")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @PostMapping(path = "/balance",produces = APPLICATION_JSON_VALUE)
    public AccountDto getAccount(@RequestBody @Valid UserDto userDto) {

        logger.info("Get User Account Details");

        return atmservice.getAccountDetails(userDto);
    }

    @Operation(tags="atm-rest-controller",summary = "cashWithdrawal",description = "rest call to withdraw the cash")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @PostMapping(path = "/withdraw",produces = APPLICATION_JSON_VALUE)
    public AccountDto cashWithdrawal(@RequestBody @Valid UserDto userDto) {

        logger.info("Proceed with the Cash Withdrawal");

        return atmservice.cashWithdrawal(userDto);
    }

}
