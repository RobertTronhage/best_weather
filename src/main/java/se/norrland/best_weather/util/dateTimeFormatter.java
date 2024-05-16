package se.norrland.best_weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dateTimeFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getFormattedDateTime(LocalDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }
}
