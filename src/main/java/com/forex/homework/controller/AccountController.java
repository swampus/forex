package com.forex.homework.controller;

import com.forex.homework.dto.AccountDTO;
import com.forex.homework.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/user/{userIdentifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDTO> getAccountsByUserIdentifier(@PathVariable UUID userIdentifier) {
        var accounts = accountService.getAccountsByUserIdentifier(userIdentifier);
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());
    }

}
