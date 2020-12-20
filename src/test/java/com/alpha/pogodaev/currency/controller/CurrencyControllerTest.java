package com.alpha.pogodaev.currency.controller;

import com.alpha.pogodaev.currency.dto.CurrencyDTO;
import com.alpha.pogodaev.currency.dto.GifDTO;
import com.alpha.pogodaev.currency.feign.CurrencyClient;
import com.alpha.pogodaev.currency.feign.GifClient;
import com.alpha.pogodaev.currency.service.GIfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.alpha.pogodaev.currency.TestData.APP_ID;
import static com.alpha.pogodaev.currency.TestData.BASE;
import static com.alpha.pogodaev.currency.TestData.GIF_APP_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    GIfService service;

    @MockBean
    CurrencyClient currencyClient;

    @MockBean
    GifClient gifClient;

    @Autowired
    private ResourceLoader resourceLoader;


    protected String getResourceFileContextAsString(String filepath) throws IOException {
        File file = getResourceFile(filepath);
        return FileUtils.readFileToString(file, String.valueOf(Charset.availableCharsets().get("UTF-8")));
    }

    protected File getResourceFile(String filepath) throws IOException {
        return resourceLoader.getResource(filepath).getFile();
    }

    @Before
    public void init() throws Exception {
        String prev = getResourceFileContextAsString("classpath:testData/historical.json");
        String late = getResourceFileContextAsString("classpath:testData/latest.json");
        String broke = getResourceFileContextAsString("classpath:testData/rich.json");
        String rich = getResourceFileContextAsString("classpath:testData/broke.json");
        GifDTO gifRich = objectMapper.readValue(rich, GifDTO.class);
        GifDTO gifBroke = objectMapper.readValue(broke, GifDTO.class);
        CurrencyDTO previous = objectMapper.readValue(prev, CurrencyDTO.class);
        CurrencyDTO latest = objectMapper.readValue(late, CurrencyDTO.class);

        when(currencyClient.getLatestRate(APP_ID, BASE)).thenReturn(latest);
        when(currencyClient.getYesterdayRate(APP_ID, BASE, LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))).thenReturn(previous);
        when(gifClient.getGif(GIF_APP_ID, "rich")).thenReturn(gifRich);
        when(gifClient.getGif(GIF_APP_ID, "broke")).thenReturn(gifBroke);
        when(service.getGif(gifRich)).thenReturn("https://media3.giphy.com/media/ZGH8VtTZMmnwzsYYMf/giphy.gif?cid=3f69f9402plriixdv64jkjfx92f8ocikdrve76f11jirmqnl&rid=giphy.gif");
        when(service.getGif(gifBroke)).thenReturn("https://media0.giphy.com/media/5885nYOgBHdCw/giphy.gif?cid=3f69f940p6ocdwb0fvz1stqtpobz0awoaft9dn1bsefhto05&rid=giphy.gif");
    }

    @Test
    void testCurrency() throws Exception {
        MvcResult res = mockMvc.perform(
                get("/latest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result = res.toString();
        Assert.assertNotNull(result);
    }
}