package com.samkt.projectmanagement.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String extractDate(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
