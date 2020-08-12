package app.dtrgenerator.util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import app.dtrgenerator.constant.Constant;

public class DateTimeUtil {
    public static LocalTime parseTime(String timeString) {
        Integer timeInteger = Integer.parseInt(timeString.replace(":", ""));
        LocalTime time = LocalTime.parse(String.format("%04d", timeInteger), DateTimeFormatter.ofPattern("HHmm"));
        if (time.isAfter(LocalTime.of(00, 00, 00)) && time.isBefore(LocalTime.of(07, 30, 00))) {
            time = time.plusHours(12);
        }

        return time;
    }

    public static Date parseDate(String dateString, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isDateInBetween(Date testDate, Date startDate, Date endDate) {
        return !testDate.before(startDate) && !testDate.after(endDate);
    }

    public static String dateRangeToString(Date startDate, Date endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatDate(startDate, Constant.DATE_FORMAT_MONTH_DAY));
        stringBuilder.append(" - ");
        stringBuilder.append(formatDate(endDate, Constant.DATE_FORMAT_FULL));

        return stringBuilder.toString();
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatTime(LocalTime time, String format) {
        return time.format(DateTimeFormatter.ofPattern(format));
    }
}