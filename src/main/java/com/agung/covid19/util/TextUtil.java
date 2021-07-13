/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author agung
 */
public class TextUtil {

    //yyyy-MM-dd'T'HH:mm:ss'Z
    static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", new Locale("ID"));
    static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", new Locale("ID"));
    static DecimalFormat format = new DecimalFormat("#,###,##0");

    public static String formatDate(String date) {
        LocalDateTime localDate = LocalDateTime.parse(date, inputFormatter);
        return outputFormatter.format(localDate);
    }

    public static String formatDecimal(Long value) {
        return format.format(value);
    }

    public static double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

}
