package app.dtrgenerator.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import app.dtrgenerator.constant.DateFormat;

public class DateTimeUtil {
    private final static LocalTime MIDNIGHT = LocalTime.of(0, 0);
    private final static LocalTime FIRST_SESSION = LocalTime.of(7, 30);

    public static LocalTime parseTime(String timeString) {
        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("H:mm"));
        if (time.isAfter(MIDNIGHT) && time.isBefore(FIRST_SESSION))
            time = time.plusHours(12);
        return time;
    }

    public static Date parseDate(String dateString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateString);
    }

    public static boolean isInBetween(Date date, Date startDate, Date endDate) {
        return !date.before(startDate) && !date.after(endDate);
    }

    public static String dateRangeToString(Date startDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatDate(startDate, DateFormat.MONTH_DAY));
        stringBuilder.append(" - ");
        stringBuilder.append(formatDate(endDate, DateFormat.FULL));
        return stringBuilder.toString();
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatTime(LocalTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}