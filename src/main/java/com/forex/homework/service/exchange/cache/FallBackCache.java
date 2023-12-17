package com.forex.homework.service.exchange.cache;

import com.forex.homework.dto.ExchangeRateDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FallBackCache {
    private final Map<String, ExchangeRateDTO> fallbackCache = new ConcurrentHashMap<>();

    public void put(String currency, ExchangeRateDTO rate) {
        fallbackCache.put(currency, rate);
    }

    public ExchangeRateDTO get(String currency) {
        return fallbackCache.get(currency);
    }

}
