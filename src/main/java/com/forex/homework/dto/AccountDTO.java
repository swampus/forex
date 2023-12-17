package com.forex.homework.dto;

import com.forex.homework.eenum.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDTO {
    private String userIdentifier;
    private String currency;

    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime dateBalanceUpdate;

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
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
