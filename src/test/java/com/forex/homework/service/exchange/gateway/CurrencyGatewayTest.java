package com.forex.homework.service.exchange.gateway;

import com.forex.homework.dto.ExchangeRateDTO;
import com.forex.homework.service.exchange.cache.FallBackCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class CurrencyGatewayTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FallBackCache fallBackCache;

    @InjectMocks
    private CurrencyGateway currencyGateway;

    @Value("${currency.service.base.url}")
    private String currencyUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(currencyGateway, "currencyUrl", "http://example.com/currency");
        currencyGateway.initializeCache();
    }

    @Test
    void getExchangeRates_Success() {
        String currency = "USD";
        ExchangeRateDTO expectedRate = new ExchangeRateDTO(currency, "2023-01-02", createRatesMap(currency, 0.9));

        when(restTemplate.getForObject(anyString(), eq(ExchangeRateDTO.class))).thenReturn(expectedRate);

        ExchangeRateDTO actualRate = currencyGateway.getExchangeRates(currency);

        assertEquals(expectedRate, actualRate);
        verify(fallBackCache, times(1)).put(currency, expectedRate);
    }

    @Test
    void getExchangeRates_RestClientException_Fallback() {
        String currency = "USD";
        ExchangeRateDTO fallbackRate = new ExchangeRateDTO(currency, "2023-01-01", createRatesMap(currency, 1.0));

        when(restTemplate.getForObject(anyString(), eq(ExchangeRateDTO.class)))
                .thenThrow(new RestClientException("Simulated RestClientException"));
        when(fallBackCache.get(currency)).thenReturn(fallbackRate);

        ExchangeRateDTO actualRate = currencyGateway.getExchangeRates(currency);

        assertEquals(fallbackRate, actualRate);
        verify(fallBackCache, times(1)).get(currency);
        verify(fallBackCache, times(1)).put(currency, fallbackRate);
    }

    private Map<String, Double> createRatesMap(String currency, double rate) {
        Map<String, Double> ratesMap = new HashMap<>();
        ratesMap.put(currency, rate);
        return ratesMap;
    }
}