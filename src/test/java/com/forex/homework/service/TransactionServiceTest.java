package com.forex.homework.service;

import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.model.Account;
import com.forex.homework.model.Transaction;
import com.forex.homework.repository.TransactionRepository;
import com.forex.homework.service.exchange.ExchangeRatesService;
import com.forex.homework.service.utils.DatetimeProvider;
import com.forex.homework.service.utils.UUIDprovider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExchangeRatesService exchangeRatesService;

    @Mock
    private AccountService accountService;

    @Mock
    private DatetimeProvider datetimeProvider;

    @Mock
    private UUIDprovider uuiDprovider;

    @Mock
    private TransactionProcessor transactionProcessor;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transferFunds() {
        String sourceAccountNumber = "USD123456789";
        String targetAccountNumber = "EUR987654321";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        String currency = "USD";

        Account sourceAccount = createAccount(sourceAccountNumber, "EUR");
        Account targetAccount = createAccount(targetAccountNumber, "USD");

        when(accountService.getAccountByAccountNumber(eq(sourceAccountNumber))).thenReturn(Optional.of(sourceAccount));
        when(accountService.getAccountByAccountNumber(eq(targetAccountNumber))).thenReturn(Optional.of(targetAccount));
        when(exchangeRatesService.getExchangeRate(eq("USD"), eq("EUR"))).thenReturn(BigDecimal.valueOf(0.85));
        when(exchangeRatesService.getExchangeRate(eq("EUR"), eq("USD"))).thenReturn(BigDecimal.valueOf(1.12));
        when(uuiDprovider.getUUUID()).thenReturn(UUID.randomUUID());
        when(datetimeProvider.getApplicationDate()).thenReturn(null);

        transactionService.transferFunds(sourceAccountNumber, targetAccountNumber, amount, currency);

        verify(accountService, times(1)).getAccountByAccountNumber(eq(sourceAccountNumber));
        verify(accountService, times(1)).getAccountByAccountNumber(eq(targetAccountNumber));
        verify(exchangeRatesService, times(1)).getExchangeRate(eq("EUR"), eq("USD"));
        verify(uuiDprovider, times(1)).getUUUID();
        verify(datetimeProvider, times(2)).getApplicationDate();

        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(transactionProcessor, times(1)).processTransactions(anyList());
    }

    private Account createAccount(String accountNumber, String currency) {
        Account account = new Account();
        account.setUserIdentifier(UUID.randomUUID());
        account.setCurrency(currency);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setStatus(AccountStatus.ACTIVE);
        account.setDateBalanceUpdate(LocalDateTime.now());
        return account;
    }
}