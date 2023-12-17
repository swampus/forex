package com.forex.homework.repository;

import com.forex.homework.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAccountNumberOrderByTransactionDateDesc(String accountNumber, Pageable pageable);

}