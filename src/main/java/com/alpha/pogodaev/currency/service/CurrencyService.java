package com.alpha.pogodaev.currency.service;

import com.alpha.pogodaev.currency.dto.CurrencyDTO;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    public Boolean compareCurrencyRates(CurrencyDTO latest, CurrencyDTO previous) {
        Double latestCurrency = latest.getRates().get("RUB");
        Double previousCurrency = previous.getRates().get("RUB");
        return latestCurrency > previousCurrency;
    }
}
