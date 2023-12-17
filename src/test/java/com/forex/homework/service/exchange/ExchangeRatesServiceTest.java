package com.forex.homework.service.exchange;

import com.forex.homework.dto.ExchangeRateDTO;
import com.forex.homework.exception.ExchangeRateNotFoundException;
import com.forex.homework.service.exchange.gateway.CurrencyGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ExchangeRatesServiceTest {

    @Mock
    private CurrencyGateway currencyGateway;

    @InjectMocks
    private ExchangeRatesService exchangeRatesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRate_Success() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        ExchangeRateDTO sourceRates = createExchangeRateDTO(sourceCurrency, createRatesMap(targetCurrency, 0.85));

        when(currencyGateway.getExchangeRates(sourceCurrency)).thenReturn(sourceRates);

        BigDecimal actualRate = exchangeRatesService.getExchangeRate(sourceCurrency, targetCurrency);

        BigDecimal expectedRate = BigDecimal.valueOf(0.85);
        assertEquals(expectedRate, actualRate);
    }

    @Test
    void getExchangeRate_RateNotFound() {
        String sourceCurrency = "USD";
        String targetCurrency = "XYZ";
        ExchangeRateDTO sourceRates = createExchangeRateDTO(sourceCurrency, createRatesMap("EUR", 0.85));

        when(currencyGateway.getExchangeRates(sourceCurrency)).thenReturn(sourceRates);

        ExchangeRateNotFoundException exception = assertThrows(ExchangeRateNotFoundException.class,
                () -> exchangeRatesService.getExchangeRate(sourceCurrency, targetCurrency));

        String expectedMessage = "Exchange rate not found for " + sourceCurrency + " to " + targetCurrency;
        assertEquals(expectedMessage, exception.getMessage());
    }

    private ExchangeRateDTO createExchangeRateDTO(String baseCurrency, Map<String, Double> rates) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setBase(baseCurrency);
        exchangeRateDTO.setDate("2023-01-01");
        exchangeRateDTO.setRates(rates);
        return exchangeRateDTO;
    }

    private Map<String, Double> createRatesMap(String currency, double rate) {
        Map<String, Double> ratesMap = new HashMap<>();
        ratesMap.put(currency, rate);
        return ratesMap;
    }
}