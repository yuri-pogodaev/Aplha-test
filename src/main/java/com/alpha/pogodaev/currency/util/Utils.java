package com.alpha.pogodaev.currency.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().minusDays(1).format(formatter);
    }

    public static int getRandom() {
        Random rand = new Random();
        return  rand.nextInt(50);
    }

}
