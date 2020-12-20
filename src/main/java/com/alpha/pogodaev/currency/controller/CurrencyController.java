package com.alpha.pogodaev.currency.controller;


import com.alpha.pogodaev.currency.dto.CurrencyDTO;
import com.alpha.pogodaev.currency.dto.GifDTO;
import com.alpha.pogodaev.currency.feign.CurrencyClient;
import com.alpha.pogodaev.currency.feign.GifClient;
import com.alpha.pogodaev.currency.service.CurrencyService;
import com.alpha.pogodaev.currency.service.GIfService;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alpha.pogodaev.currency.util.Utils.getCurrentDate;

@RestController
@Import(FeignClientsConfiguration.class)
public class CurrencyController {

    private final String base;
    private final String currencyAppId;
    private final String gifAppId;
    private final String currencyUrl;
    private final String gifUrl;
    private final GIfService gIfService;
    private final CurrencyClient currencyClient;
    private final GifClient gifClient;
    private final CurrencyService currencyService;

    public CurrencyController(Contract contract, Decoder decoder, Encoder encoder,
                              @Value("${currency.base}") String base,
                              @Value("${currency.app_id}") String currencyAppId,
                              @Value("${giphy.app_id}") String gifAppId,
                              @Value("${currency.url}") String currencyUrl,
                              @Value("${gif.url}") String gifUrl,
                              GIfService gIfService,
                              CurrencyService currencyService) {
        this.base = base;
        this.currencyAppId = currencyAppId;
        this.gifAppId = gifAppId;
        this.currencyUrl = currencyUrl;
        this.gifUrl = gifUrl;
        this.gIfService = gIfService;
        this.currencyService = currencyService;
        this.currencyClient = Feign.builder()
                .contract(contract).decoder(decoder).encoder(encoder)
                .target(CurrencyClient.class, currencyUrl);
        this.gifClient = Feign.builder()
                .contract(contract).decoder(decoder).encoder(encoder)
                .target(GifClient.class, gifUrl);
    }

    @GetMapping("/latest")
    public String getManualData() {
        CurrencyDTO latest = currencyClient.getLatestRate(currencyAppId, base);
        CurrencyDTO previous = currencyClient.getYesterdayRate(getCurrentDate(), currencyAppId, base);

        String searchKey = currencyService.compareCurrencyRates(latest, previous) ? "broke" : "rich";

        GifDTO dto = gifClient.getGif(gifAppId, searchKey);
        return gIfService.getGif(dto);
    }
}
