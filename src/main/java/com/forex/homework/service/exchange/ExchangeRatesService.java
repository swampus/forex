package com.forex.homework.service.exchange;

import com.forex.homework.dto.ExchangeRateDTO;
import com.forex.homework.exception.ExchangeRateNotFoundException;
import com.forex.homework.service.exchange.gateway.CurrencyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeRatesService {
    @Autowired
    private CurrencyGateway currencyGateway;
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {
        ExchangeRateDTO sourceRates = currencyGateway.getExchangeRates(sourceCurrency);
        Double sourceToTargetRate = sourceRates.getRates().get(targetCurrency);

        if (sourceToTargetRate != null) {
            return BigDecimal.valueOf(sourceToTargetRate);
        } else {
            throw new ExchangeRateNotFoundException("Exchange rate not found for " + sourceCurrency + " to " + targetCurrency);
        }

    }
}
