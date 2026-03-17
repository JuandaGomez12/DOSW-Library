package edu.eci.dosw.DOSW_Library.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtil() {}

    public static String today() {
        return LocalDate.now().format(FORMATTER);
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }
}