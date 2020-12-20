package com.alpha.pogodaev.currency.dto;

import lombok.Value;

import java.util.Map;

@Value
public class CurrencyDTO {
    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    private Map<String, Double> rates;
}
