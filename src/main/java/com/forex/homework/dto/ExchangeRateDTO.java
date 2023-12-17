package com.forex.homework.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExchangeRateDTO {
    private String base;
    private String date;
    private Map<String, Double> rates;

    public ExchangeRateDTO() {
    }

    public ExchangeRateDTO(String base, String date, Map<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateDTO that = (ExchangeRateDTO) o;
        return Objects.equals(base, that.base) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, date);
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
