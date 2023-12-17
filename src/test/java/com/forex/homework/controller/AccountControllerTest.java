package com.forex.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forex.homework.TestConfig;
import com.forex.homework.eenum.AccountStatus;
import com.forex.homework.model.Account;
import com.forex.homework.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
@WebMvcTest(AccountController.class)
@Import(TestConfig.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAccountsByUserIdentifier() throws Exception {

        UUID userIdentifier = UUID.randomUUID();
        List<Account> mockAccounts = Arrays.asList(
                new Account(1L, userIdentifier, "USD", "123456", BigDecimal.valueOf(1000.00), AccountStatus.ACTIVE, LocalDateTime.now()),
                new Account(2L, userIdentifier, "EUR", "789012", BigDecimal.valueOf(500.00), AccountStatus.ACTIVE, LocalDateTime.now())
        );

        when(accountService.getAccountsByUserIdentifier(userIdentifier)).thenReturn(mockAccounts);

        mockMvc.perform(get("/rest/api/accounts/user/{userIdentifier}", userIdentifier)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userIdentifier", is(userIdentifier.toString())))
                .andExpect(jsonPath("$[0].currency", is("USD")))
                .andExpect(jsonPath("$[0].accountNumber", is("123456")))
                .andExpect(jsonPath("$[0].balance", is(1000.00)))
                .andExpect(jsonPath("$[0].status", is("ACTIVE")))
                .andExpect(jsonPath("$[1].userIdentifier", is(userIdentifier.toString())))
                .andExpect(jsonPath("$[1].currency", is("EUR")))
                .andExpect(jsonPath("$[1].accountNumber", is("789012")))
                .andExpect(jsonPath("$[1].balance", is(500.00)))
                .andExpect(jsonPath("$[1].status", is("ACTIVE")));

        verify(accountService, times(1)).getAccountsByUserIdentifier(userIdentifier);
        verifyNoMoreInteractions(accountService);
    }
}