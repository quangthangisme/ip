package mightyduck.utils;

import java.time.format.DateTimeFormatter;

/**
 * Utility class for handling date-time formatting.
 */
public class DateTimeUtils {

    /**
     * The common datetime format used throughout the application.
     */
    public static final String PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * The common datetime formatter used throughout the application.
     */
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Private constructor to prevent instantiation of this utility class
     */
    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
