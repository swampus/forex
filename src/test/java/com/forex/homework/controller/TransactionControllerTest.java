package com.forex.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.homework.TestConfig;
import com.forex.homework.dto.TransferDTO;
import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.eenum.TransactionStatus;
import com.forex.homework.eenum.TransactionType;
import com.forex.homework.model.Account;
import com.forex.homework.model.Transaction;
import com.forex.homework.repository.TransactionRepository;
import com.forex.homework.service.AccountService;
import com.forex.homework.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;

@WebMvcTest(TransactionController.class)
@Import(TestConfig.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    private static final UUID userUuid = UUID.randomUUID();

    @Test
    void testGetTransactionHistory() throws Exception {

        String accountNumber = "EURACCTEST";
        int offset = 0;
        int limit = 10;

        List<Transaction> mockTransactions = generateMockTransactions(15);

        Pageable pageable = PageRequest.of(offset, limit);
        PageImpl<Transaction> page = new PageImpl<>(mockTransactions, pageable, mockTransactions.size());

        when(transactionService.getTransactionHistory(accountNumber, 0, 10))
                .thenReturn(page.getContent());

        ResultActions resultActions = mockMvc.perform(get("/rest/api/transactions/history/{accountNumber}", accountNumber)
                .param("offset", String.valueOf(offset))
                .param("limit", String.valueOf(limit)));


        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(mockTransactions.size())))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("SCHEDULED"))
                .andExpect(jsonPath("$[0].account").value(accountNumber))
                .andExpect(jsonPath("$[0].amount").value(110.0))
                .andExpect(jsonPath("$[0].blockIdentifier").value("..."))
                .andExpect(jsonPath("$[0].type").value("CURRENCY_EXCHANGE"))
                .andExpect(jsonPath("$[0].transactionDate").value("..."))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].executed").value(true))
                .andExpect(jsonPath("$[0].exchangeRate").value(1.0));

        for (int i = 1; i < mockTransactions.size(); i++) {
            int expectedId = i + 1;
            resultActions.andExpect(jsonPath("$[" + i + "].id").value(expectedId));
        }

        Mockito.verify(transactionService, Mockito.times(1))
                .getTransactionHistory(accountNumber, 0, 10);
    }

    public static List<Transaction> generateMockTransactions(int count) {
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setId((long) (i + 1));
            transaction.setAccount(generateMockAccount());
            transaction.setAmount(BigDecimal.valueOf(110));
            transaction.setBlockIdentifier(UUID.randomUUID());
            transaction.setType(TransactionType.CURRENCY_EXCHANGE);
            transaction.setStatus(TransactionStatus.SCHEDUED);
            transaction.setTransactionDate(new Date());
            transaction.setCurrency("USD");
            transaction.setExecuted(true);
            transaction.setExchangeRate(BigDecimal.valueOf(1.0));
            transactions.add(transaction);
        }

        return transactions;
    }

    private static Account generateMockAccount() {
        Account account = new Account();
        account.setUserIdentifier(userUuid);
        account.setCurrency("EUR");
        account.setAccountNumber("EURACCTEST");
        account.setBalance(BigDecimal.valueOf(1000.0));
        account.setStatus(AccountStatus.ACTIVE);
        account.setDateBalanceUpdate(LocalDateTime.now());
        return account;
    }

    @Test
    void testTransfer() throws Exception {

        TransferDTO transferDTO = createSampleTransferDTO();

        doNothing().when(transactionService).transferFunds(anyString(), anyString(), any(), anyString());

        mockMvc.perform(post("/rest/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transferDTO)))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).transferFunds(
                eq(transferDTO.getSourceAccountNumber()),
                eq(transferDTO.getTargetAccountNumber()),
                eq(transferDTO.getAmount()),
                eq(transferDTO.getCurrency())
        );

    }

    private TransferDTO createSampleTransferDTO() {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAmount(BigDecimal.valueOf(100));
        transferDTO.setSourceAccountNumber("USD234567890");
        transferDTO.setTargetAccountNumber("EUR234567890");
        transferDTO.setCurrency("EUR");
        return transferDTO;
    }


}