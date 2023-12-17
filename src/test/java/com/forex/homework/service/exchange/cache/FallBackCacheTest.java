package com.forex.homework.service.exchange.cache;

import com.forex.homework.dto.ExchangeRateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FallBackCacheTest {

    private FallBackCache fallBackCache;

    @BeforeEach
    void setUp() {
        fallBackCache = new FallBackCache();
    }

    @Test
    void putAndGet() {
        String currency = "USD";
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(currency, "2023-01-01", createRatesMap(currency, 1.0));

        fallBackCache.put(currency, exchangeRateDTO);

        ExchangeRateDTO retrievedRate = fallBackCache.get(currency);

        assertNotNull(retrievedRate);
        assertEquals(exchangeRateDTO.getBase(), retrievedRate.getBase());
        assertEquals(exchangeRateDTO.getDate(), retrievedRate.getDate());
        assertEquals(exchangeRateDTO.getRates().get(currency), retrievedRate.getRates().get(currency));
    }

    @Test
    void getNonExistingCurrency() {
        String nonExistingCurrency = "BLACK_DRAGON_COIN";

        ExchangeRateDTO retrievedRate = fallBackCache.get(nonExistingCurrency);

        assertNull(retrievedRate);
    }

    private Map<String, Double> createRatesMap(String currency, double rate) {
        Map<String, Double> ratesMap = new HashMap<>();
        ratesMap.put(currency, rate);
        return ratesMap;
    }
}