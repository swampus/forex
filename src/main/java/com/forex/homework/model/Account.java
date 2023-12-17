package com.forex.homework.model;

import com.forex.homework.eenum.AccountStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_identifier", nullable = false)
    private UUID userIdentifier;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "date_balance_update")
    private LocalDateTime dateBalanceUpdate;

    public Account() {
    }

    public Account(Long id, UUID userIdentifier, String currency, String accountNumber,
                   BigDecimal balance, AccountStatus status, LocalDateTime dateBalanceUpdate) {
        this.id = id;
        this.userIdentifier = userIdentifier;
        this.currency = currency;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.dateBalanceUpdate = dateBalanceUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(UUID userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateBalanceUpdate() {
        return dateBalanceUpdate;
    }

    public void setDateBalanceUpdate(LocalDateTime dateBalanceUpdate) {
        this.dateBalanceUpdate = dateBalanceUpdate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}