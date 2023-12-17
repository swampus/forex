package com.forex.homework.service;

import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.eenum.TransactionStatus;
import com.forex.homework.model.Account;
import com.forex.homework.model.Transaction;
import com.forex.homework.repository.AccountRepository;
import com.forex.homework.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionProcessorTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processTransactions_Success() {
        List<Transaction> transactions = Arrays.asList(
                createTransaction(1L, BigDecimal.valueOf(100)),
                createTransaction(2L, BigDecimal.valueOf(200))
        );

        for (Transaction transaction : transactions) {
            Account account = transaction.getAccount();
            when(accountRepository.save(account)).thenReturn(account);
            when(transactionRepository.save(transaction)).thenReturn(transaction);
        }

        transactionProcessor.processTransactions(transactions);

        for (Transaction transaction : transactions) {
            assertEquals(TransactionStatus.DONE, transaction.getStatus());
        }

        verify(accountRepository, times(transactions.size())).save(any(Account.class));
        verify(transactionRepository, times(transactions.size())).save(any(Transaction.class));
    }

    @Test
    void processTransactions_Exception() {
        List<Transaction> transactions = Arrays.asList(
                createTransaction(1L, BigDecimal.valueOf(100)),
                createTransaction(2L, BigDecimal.valueOf(200))
        );

        for (Transaction transaction : transactions) {
            Account account = transaction.getAccount();
            when(accountRepository.save(account)).thenThrow(new RuntimeException("Simulated exception"));
        }

        transactionProcessor.processTransactions(transactions);

        for (Transaction transaction : transactions) {
            assertEquals(TransactionStatus.FAILED, transaction.getStatus());
        }

        verify(accountRepository, times(transactions.size())).save(any(Account.class));
        verify(transactionRepository, times(transactions.size())).save(any(Transaction.class));
    }

    private Transaction createTransaction(Long id, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setAccount(createAccount());
        transaction.setStatus(TransactionStatus.SCHEDUED);
        return transaction;
    }

    private Account createAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setUserIdentifier(UUID.randomUUID());
        account.setCurrency("USD");
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.valueOf(1000.00));
        account.setStatus(AccountStatus.ACTIVE);
        account.setDateBalanceUpdate(LocalDateTime.now());
        return account;
    }
}