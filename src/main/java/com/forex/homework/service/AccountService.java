package com.forex.homework.service;

import com.forex.homework.model.Account;
import com.forex.homework.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccountsByUserIdentifier(UUID userIdentifier) {
        return accountRepository.findByUserIdentifier(userIdentifier);
    }

    public Optional<Account> getAccountByAccountNumber(String sourceAccountNumber) {
        return Optional.ofNullable(accountRepository.getAccountByAccountNumber(sourceAccountNumber));
    }
}
