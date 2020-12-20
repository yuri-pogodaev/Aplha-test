package com.alpha.pogodaev.currency.service;

import com.alpha.pogodaev.currency.dto.GifDTO;
import org.springframework.stereotype.Service;

import static com.alpha.pogodaev.currency.util.Utils.getRandom;

@Service
public class GIfService {

    public String getGif(GifDTO gif) {
        return gif.getData().get(getRandom()).getBitly_gif_url();
    }
}
