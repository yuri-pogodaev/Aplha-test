package com.alpha.pogodaev.currency.feign;

import com.alpha.pogodaev.currency.dto.CurrencyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("currency")
public interface CurrencyClient {

    @GetMapping("/latest.json?app_id={app_id}&base={base}")
    CurrencyDTO getLatestRate(@PathVariable String app_id, @PathVariable String base);

    @GetMapping("/historical/{date}.json?app_id={app_id}&base={base}")
    CurrencyDTO getYesterdayRate(@PathVariable String date, @PathVariable String app_id, @PathVariable String base);

}
