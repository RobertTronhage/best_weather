package se.norrland.best_weather.util;

import java.time.LocalDateTime;

public class DateTimeFormatter {

    public static final java.time.format.DateTimeFormatter FORMATTER =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getFormattedDateTime(LocalDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }
}
