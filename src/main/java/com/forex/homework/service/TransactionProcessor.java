package com.forex.homework.service;

import com.forex.homework.eenum.TransactionStatus;
import com.forex.homework.model.Transaction;
import com.forex.homework.repository.AccountRepository;
import com.forex.homework.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionProcessor {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    public void processTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            try {
                // Update account balances
                var account = transaction.getAccount();
                var newBalance = account.getBalance().add(transaction.getAmount());
                account.setBalance(newBalance);

                // Update transaction status
                transaction.setStatus(TransactionStatus.DONE);

                // Save the updated account and transaction
                accountRepository.save(account);

            } catch (Exception e) {
                //Later ROLLBACK logic will pick-up failed transactions
                //+DB logging
                transaction.setStatus(TransactionStatus.FAILED);
            }finally {
                transactionRepository.save(transaction);
            }
        }
    }
}
