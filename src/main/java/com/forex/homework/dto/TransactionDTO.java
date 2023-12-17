package com.forex.homework.dto;

import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.eenum.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {
    private Long id;
    private String blockIdentifier;
    private Long accountId;
    private BigDecimal amount;
    private Date transactionDate;
    private String currency;

    private String status;
    private BigDecimal exchangeRate;

    private TransactionType type;

    private Boolean executed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public String getBlockIdentifier() {
        return blockIdentifier;
    }

    public void setBlockIdentifier(String blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}