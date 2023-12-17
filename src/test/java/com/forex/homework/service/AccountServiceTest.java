package com.forex.homework.service;

import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.model.Account;
import com.forex.homework.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountsByUserIdentifier() {
        UUID userIdentifier = UUID.randomUUID();
        List<Account> expectedAccounts = Arrays.asList(
                new Account(1L, userIdentifier, "USD", "123456", BigDecimal.valueOf(1000.00), AccountStatus.ACTIVE, LocalDateTime.now()),
                new Account(2L, userIdentifier, "EUR", "789012", BigDecimal.valueOf(500.00), AccountStatus.ACTIVE, LocalDateTime.now())
        );

        when(accountRepository.findByUserIdentifier(userIdentifier)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.getAccountsByUserIdentifier(userIdentifier);

        assertEquals(expectedAccounts.size(), actualAccounts.size());
        assertEquals(expectedAccounts, actualAccounts);
        verify(accountRepository, times(1)).findByUserIdentifier(userIdentifier);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void getAccountByAccountNumber() {
        String accountNumber = "123456";
        Account expectedAccount = new Account(1L, UUID.randomUUID(), "USD", accountNumber,
                BigDecimal.valueOf(1000.00), AccountStatus.ACTIVE, LocalDateTime.now());

        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

        Optional<Account> actualAccount = accountService.getAccountByAccountNumber(accountNumber);

        assertTrue(actualAccount.isPresent());
        assertEquals(expectedAccount, actualAccount.get());
        verify(accountRepository, times(1)).getAccountByAccountNumber(accountNumber);
        verifyNoMoreInteractions(accountRepository);
    }
}