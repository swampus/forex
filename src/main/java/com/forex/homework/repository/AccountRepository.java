package com.forex.homework.repository;

import com.forex.homework.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserIdentifier(UUID userIdentifier);
    Account getAccountByAccountNumber(String accountNumber);
}
