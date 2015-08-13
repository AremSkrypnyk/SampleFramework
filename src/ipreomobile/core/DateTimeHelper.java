package ipreomobile.core;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateTimeHelper {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);

    public static DateTimeFormatter TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    public static DateTimeFormatter TIME_FORMATTER_WITHOUT_SPACE_BEFORE_AM_PM = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    public static DateTimeFormatter DATE_TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a", Locale.ENGLISH);
    public static DateTimeFormatter DATE_TIME_FORMATTER_WITHOUT_SPACE_BEFORE_AM_PM = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma", Locale.ENGLISH);

    public static List<DateTimeFormatter> getDateFormatters(){
        return new ArrayList<>(Arrays.asList(
                DATE_FORMATTER
        ));
    }

    public static List<DateTimeFormatter> getTimeFormatters(){
        return new ArrayList<>(Arrays.asList(
                TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM,
                TIME_FORMATTER_WITHOUT_SPACE_BEFORE_AM_PM
        ));
    }

    public static List<DateTimeFormatter> getDateTimeFormatters(){
        return new ArrayList<>(Arrays.asList(
                DATE_TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM,
                DATE_TIME_FORMATTER_WITHOUT_SPACE_BEFORE_AM_PM
        ));
    }

    public static boolean compareDateTimeValues(String actualDateTimeStr, String expectedDateTimeStr, String errorMessage){
        boolean result;
        LocalDateTime actual = parseDateTimeStr(actualDateTimeStr);
        LocalDateTime expected = parseDateTimeStr(expectedDateTimeStr);
        result = actual.equals(expected);
        if (!result){
            Logger.logError(errorMessage + "\nExpected "+expectedDateTimeStr+", but found "+actualDateTimeStr+".");
        }
        return result;
    }

    public static boolean compareDateValues(String actualDateStr, String expectedDateStr, String errorMessage){
        boolean result;
        LocalDate actual = parseDateStr(actualDateStr);
        LocalDate expected = parseDateStr(expectedDateStr);
        result = actual.equals(expected);
        if (!result){
            Logger.logError(errorMessage + "\nExpected "+expectedDateStr+", but found "+actualDateStr+".");
        }
        return result;
    }

    public static boolean compareTimeValues(String actualTimeStr, String expectedTimeStr, String errorMessage){
        boolean result;
        LocalTime actual = parseTimeStr(actualTimeStr);
        LocalTime expected = parseTimeStr(expectedTimeStr);
        result = actual.equals(expected);
        if (!result){
            Logger.logError(errorMessage + "\nExpected "+expectedTimeStr+", but found "+actualTimeStr+".");
        }
        return result;
    }

    public static String getDateFromDateTimeStr(String dateTimeStr){
        Temporal dateTime;
        try {
            dateTime = parseDateTimeStr(dateTimeStr);
        } catch (DateTimeParseException e) {
            dateTime = parseDateStr(dateTimeStr);
        }
        DateTimeFormatter outputFormatter = DATE_FORMATTER;
        return outputFormatter.format(dateTime);
    }

    public static String getTimeFromDateTimeStr(String dateTimeStr){
        Temporal dateTime = parseDateTimeStr(dateTimeStr);
        DateTimeFormatter outputFormatter = TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM;
        return outputFormatter.format(dateTime);
    }

    public static String getCurrentDateStr() {
        DateTimeFormatter outputFormatter = DATE_FORMATTER;
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(outputFormatter);
    }

    public static String getCurrentTimeStr() {
        DateTimeFormatter outputFormatter = TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM;
        LocalTime currentTime = LocalTime.now();
        return outputFormatter.format(currentTime);
    }

    public static String addDaysToCurrentDate(int numberOfDays) {
        DateTimeFormatter outputFormatter = DATE_FORMATTER;
        LocalDate date = LocalDate.now();
        date = date.plusDays(numberOfDays);
        return date.format(outputFormatter);
    }

    public static String getDayFromDateStr(String dateStr) {
        Temporal date;
        try {
            date = parseDateStr(dateStr);
        } catch (DateTimeParseException e) {
            date = parseDateTimeStr(dateStr);
        }
        return DateTimeFormatter.ofPattern("dd", Locale.ENGLISH).format(date);
    }

    public static String getMonthFromDateStr(String dateStr) {
        Temporal date;
        try {
            date = parseDateStr(dateStr);
        } catch (DateTimeParseException e) {
            date = parseDateTimeStr(dateStr);
        }
        return DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH).format(date);
    }

    public static String getYearFromDateStr(String dateStr) {
        Temporal date;
        try {
            date = parseDateStr(dateStr);
        } catch (DateTimeParseException e) {
            date = parseDateTimeStr(dateStr);
        }
        return DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH).format(date);
    }

    public static String getMonthDayFromDateStr(String dateStr) {
        Temporal date;
        try {
            date = parseDateStr(dateStr);
        } catch (DateTimeParseException e) {
            date = parseDateTimeStr(dateStr);
        }
        return DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH).format(date);
    }

    public static String getHourFromTimeStr(String timeStr) {
        Temporal time;
        try {
            time = parseTimeStr(timeStr);
        } catch (DateTimeParseException e) {
            time = parseDateTimeStr(timeStr);
        }
        return DateTimeFormatter.ofPattern("hh", Locale.ENGLISH).format(time);
    }

    public static String getMinutesFromTimeStr(String timeStr) {
        Temporal time;
        try {
            time = parseTimeStr(timeStr);
        } catch (DateTimeParseException e) {
            time = parseDateTimeStr(timeStr);
        }
        return DateTimeFormatter.ofPattern("mm", Locale.ENGLISH).format(time);
    }

    public static String getPeriodFromTimeStr(String timeStr) {
        Temporal time;
        try {
            time = parseTimeStr(timeStr);
        } catch (DateTimeParseException e) {
            time = parseDateTimeStr(timeStr);
        }
        return DateTimeFormatter.ofPattern("a", Locale.ENGLISH).format(time);
    }

    public static String getRoundedTimeStr(String timeStr) {
        LocalTime time = parseTimeStr(timeStr);
        time = getRoundedTime(time);
        return time.format(TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM);
    }

    public static String getTimeAfterCurrent(int minutesAfter) {
        LocalTime time = LocalTime.now().plusMinutes(minutesAfter);
        time = getRoundedTime(time);
        return time.format(TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM);
    }

    public static String getTimeBeforeCurrent(int minutesBefore) {
        LocalTime time = LocalTime.now().minusMinutes(minutesBefore);
        time = getRoundedTime(time);
        return time.format(TIME_FORMATTER_WITH_SPACE_BEFORE_AM_PM);
    }

    public static String getNextSaturday() {
        LocalDate localDate = LocalDate.now();
        int todaysNumberOfDay = localDate.getDayOfWeek().getValue();
        int diffBetweenTodayAndNearestFriday = (DayOfWeek.SATURDAY.getValue() - todaysNumberOfDay) % 7;
        if (diffBetweenTodayAndNearestFriday <= 0) {
            diffBetweenTodayAndNearestFriday += 7;
        }

        return localDate.plusDays(diffBetweenTodayAndNearestFriday).format(DATE_FORMATTER);
    }

    private static LocalTime getRoundedTime(LocalTime time) {
        if (time.getMinute() % 5 != 0) {
            time = time.minusMinutes(time.getMinute() % 5);
        }
        return time;
    }

    private static Temporal getDate(String dateStr){
        Temporal date;
        try {
            date = parseDateStr(dateStr);
        } catch (DateTimeParseException e) {
            date = parseDateTimeStr(dateStr);
        }
        return date;
    }

    private static Temporal getTime(String timeStr){
        Temporal time;
        try {
            time = parseDateStr(timeStr);
        } catch (DateTimeParseException e) {
            time = parseDateTimeStr(timeStr);
        }
        return time;
    }

    private static LocalDateTime parseDateTimeStr(String dateTimeStr){
        LocalDateTime dateTime;
        for (DateTimeFormatter formatter : getDateTimeFormatters()) {
            try {
                dateTime = LocalDateTime.parse(dateTimeStr.toUpperCase(), formatter);
                return dateTime;
            } catch (DateTimeParseException e) {
                Logger.logDebug("Failed to parse '"+dateTimeStr+"' with "+ formatter.toString() + ".");
            }
        }
        throw new DateTimeParseException("Cannot parse DateTime value using any of DateTime formatters defined in the project.", dateTimeStr, -1);
    }

    private static LocalDate parseDateStr(String dateStr){
        LocalDate date;
        for (DateTimeFormatter formatter : getDateFormatters()) {
            try {
                date = LocalDate.parse(dateStr.toUpperCase(), formatter);
                return date;
            } catch (DateTimeParseException e) {
                Logger.logDebug("Failed to parse '"+dateStr+"' with "+ formatter.toString() + ".");
            }
        }
        throw new DateTimeParseException("Cannot parse DateTime value using any of DateTime formatters defined in the project.", dateStr, -1);
    }

    private static LocalTime parseTimeStr(String timeStr){
        LocalTime time;
        for (DateTimeFormatter formatter : getTimeFormatters()) {
            try {
                time = LocalTime.parse(timeStr.toUpperCase(), formatter);
                return time;
            } catch (DateTimeParseException e) {
                Logger.logDebug("Failed to parse '"+timeStr+"' with "+ formatter.toString() + ".");
            }
        }
        throw new DateTimeParseException("Cannot parse DateTime value using any of DateTime formatters defined in the project.", timeStr, -1);
    }
}
