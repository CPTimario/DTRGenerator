package app.dtrgenerator.model;

import app.dtrgenerator.constant.DateFormat;
import app.dtrgenerator.constant.TimeFormat;
import app.dtrgenerator.util.DateTimeUtil;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Session implements Comparable<Session> {
    private Date date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isValid() {
        return Objects.nonNull(date) && Objects.nonNull(timeIn) && Objects.nonNull(timeOut);
    }

    @Override
    public int compareTo(Session other) {
        int result = this.date.compareTo(other.date);
        if (result != 0)
            return result;
        else
            return this.timeIn.compareTo(other.timeIn);
    }

    public Map<String, String> toDataByColumn() {
        Map<String, String> dataByColumn = new LinkedHashMap<>();
        long duration = timeIn.until(timeOut, MINUTES);
        String sessionStatus = "";
        if (Math.abs(duration - 60) >= 10)
            sessionStatus = DateTimeUtil.getElapsedTime(duration);
        dataByColumn.put("A", DateTimeUtil.formatDate(date, DateFormat.SHORT_DASH));
        dataByColumn.put("B", DateTimeUtil.formatTime(timeIn, TimeFormat.HOUR_MINUTE));
        dataByColumn.put("C", DateTimeUtil.formatTime(timeOut, TimeFormat.HOUR_MINUTE));
        dataByColumn.put("D", DateTimeUtil.formatTime(timeIn, TimeFormat.MERIDIEM));
        dataByColumn.put("F", sessionStatus);
        return dataByColumn;
    }
}