package com.alpha.pogodaev.currency.feign;

import com.alpha.pogodaev.currency.dto.GifDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gif")
public interface GifClient {

    @GetMapping("?api_key={key}&q={search}")
    GifDTO getGif(@PathVariable String key, @PathVariable String search);

}
