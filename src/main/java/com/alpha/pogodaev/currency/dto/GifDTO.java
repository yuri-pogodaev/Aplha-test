package com.alpha.pogodaev.currency.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GifDTO {
    private List<Datas> data;

    @Data
    public static class Datas {
        private String type;
        private String id;
        private String url;
        private String slug;
        private String bitly_gif_url;
        private String bitly_url;
        private String embed_url;
        private String username;
        private String source;
        private String title;
        private String rating;
        private String content_url;
        private String source_tld;
        private String source_post_url;
        private String is_sticker;
        private String import_datetime;
        private String trending_datetime;
        private Map<String, Object> images;
    }
}
