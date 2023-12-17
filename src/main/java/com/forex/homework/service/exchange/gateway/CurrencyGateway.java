package com.forex.homework.service.exchange.gateway;

import com.forex.homework.dto.ExchangeRateDTO;
import com.forex.homework.service.exchange.cache.FallBackCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CurrencyGateway {

    @Autowired
    private FallBackCache fallBackCache;
    @Value("${currency.service.base.url}")
    private String currencyUrl;
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    //@VisibleForTesting
    void initializeCache() {
        // Emulation of init cache (can take from some reserve copy)
        fallBackCache.put("USD", new ExchangeRateDTO("USD", "2023-01-01",
                Map.of("EUR", 0.85, "GBP", 0.75)));
        fallBackCache.put("EUR", new ExchangeRateDTO("EUR", "2023-01-01",
                Map.of("USD", 1.18, "GBP", 0.89)));
    }

    public ExchangeRateDTO getExchangeRates(String currency) {
        try {
            ExchangeRateDTO rate = restTemplate.getForObject(createCallUrl(currency), ExchangeRateDTO.class);
            fallBackCache.put(currency, rate);
            return rate;
        } catch (RestClientException e) {
            return fallbackExchangeRates(currency);
        }
    }

    private String createCallUrl(String currency) {
        return String.format("%s/latest?from=%s", currencyUrl, currency.toUpperCase());
    }

    private ExchangeRateDTO fallbackExchangeRates(String currency) {
        return fallBackCache.get(currency);
    }
}
