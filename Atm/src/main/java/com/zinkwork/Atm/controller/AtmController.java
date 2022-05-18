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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.NoSuchElementException;

@Tag(name="atm-rest-controller")
@RestController
@RequestMapping("/atm")
@Validated
@RequiredArgsConstructor
public class AtmController {

    @Autowired
    private AtmService atmservice;

    @Operation(tags="atm-rest-controller",summary = "getBalance",description = "rest call to return the balance")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @PostMapping(path = "/balance",produces = APPLICATION_JSON_VALUE)
    public AccountDto getAccount(@RequestBody @Valid UserDto userDto) throws Exception {

        try {
            return atmservice.getAccountDetails(userDto);

        } catch (Exception ex){
            throw new Exception();
        }
    }

    @Operation(tags="atm-rest-controller",summary = "cashWithdrawal",description = "rest call to withdraw the cash")
    @ApiResponses(value = @ApiResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = APPLICATION_JSON_VALUE)))

    @PostMapping(path = "/withdraw",produces = APPLICATION_JSON_VALUE)
    public AccountDto cashWithdrawal(@RequestBody @Valid UserDto userDto) throws Exception {

        try {
            return atmservice.cashWithdrawal(userDto);
        } catch (NoSuchElementException ex){
            throw new Exception();
        }
    }

}
