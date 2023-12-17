package com.forex.homework.service;

import com.forex.homework.eenum.TransactionStatus;
import com.forex.homework.eenum.TransactionType;
import com.forex.homework.exception.AccountNotFoundException;
import com.forex.homework.exception.InsufficientBalanceException;
import com.forex.homework.exception.InvalidCurrencyException;
import com.forex.homework.model.Account;
import com.forex.homework.model.Transaction;
import com.forex.homework.repository.TransactionRepository;
import com.forex.homework.service.exchange.ExchangeRatesService;
import com.forex.homework.service.utils.DatetimeProvider;
import com.forex.homework.service.utils.UUIDprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DatetimeProvider datetimeProvider;

    @Autowired
    private UUIDprovider uuiDprovider;

    @Autowired
    private TransactionProcessor transactionProcessor;

    public List<Transaction> getTransactionHistory(String accountNumber, int offset, int limit) {
        var pageable = PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("transactionDate")));
        return transactionRepository.findByAccountAccountNumberOrderByTransactionDateDesc(accountNumber, pageable);
    }


    public void transferFunds(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount, String currency) {

        var sourceAccount = accountService.getAccountByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found: " + sourceAccountNumber));

        var targetAccount = accountService.getAccountByAccountNumber(targetAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Target account not found: " + targetAccountNumber));

        if (!targetAccount.getCurrency().equals(currency)) {
            throw new InvalidCurrencyException("Invalid currency for the source account: " + currency);
        }



        // Convert amount if the currencies are different
        if (!sourceAccount.getCurrency().equals(targetAccount.getCurrency())) {
            var exchangeRate = exchangeRatesService.getExchangeRate(sourceAccount.getCurrency(), targetAccount.getCurrency());
            amount = amount.multiply(exchangeRate);

            if (sourceAccount.getBalance().compareTo(amount) < 0) {
                throw new InsufficientBalanceException("Insufficient balance in the source account: " + sourceAccountNumber);
            }

            var trnBlock = uuiDprovider.getUUUID();

            var sourceTransaction = createCurrencyTransaction(sourceAccount, amount.negate(), exchangeRate, trnBlock);
            var targetTransaction = createCurrencyTransaction(targetAccount, amount, exchangeRate, trnBlock);

            extractAndProcessTransactions(sourceTransaction, targetTransaction);
        } else {
            // Currencies are the same, proceed with a regular transaction
            var trnBlock = uuiDprovider.getUUUID();
            var sourceTransaction = createCurrencyTransaction(sourceAccount, amount.negate(), BigDecimal.ONE, trnBlock);
            var targetTransaction = createCurrencyTransaction(targetAccount, amount, BigDecimal.ONE, trnBlock);
            extractAndProcessTransactions(sourceTransaction, targetTransaction);
        }
    }

    private void extractAndProcessTransactions(Transaction sourceTransaction, Transaction targetTransaction) {
        List<Transaction> list = List.of(sourceTransaction, targetTransaction);
        list.forEach(t -> {
            transactionRepository.save(t);
        });
        transactionProcessor.processTransactions(list);
    }

    private Transaction createCurrencyTransaction(Account account, BigDecimal amount,
                                                  BigDecimal exchangeRate, UUID blockIdentifier) {
        var transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setBlockIdentifier(blockIdentifier);
        transaction.setType(TransactionType.CURRENCY_EXCHANGE);
        transaction.setStatus(TransactionStatus.SCHEDUED);
        transaction.setTransactionDate(datetimeProvider.getApplicationDate());
        transaction.setCurrency(account.getCurrency());
        transaction.setExchangeRate(exchangeRate);
        return transaction;
    }
}
