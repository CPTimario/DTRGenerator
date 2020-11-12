package app.dtrgenerator.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import app.dtrgenerator.constant.DateFormat;
import app.dtrgenerator.constant.TimeFormat;
import app.dtrgenerator.util.DateTimeUtil;

public class Session implements Comparable<Session> {
    private Date date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isValid() {
        return Objects.nonNull(date) && Objects.nonNull(timeIn) && Objects.nonNull(timeOut);
    }

    @Override
    public int compareTo(Session other) {
        int result = this.getDate().compareTo(other.getDate());
        if (result != 0)
            return result;
        else
            return this.getTimeIn().compareTo(other.getTimeIn());
    }

    public Map<String, String> toDataByColumn() {
        Map<String, String> dataByColumn = new LinkedHashMap<>();
        dataByColumn.put("A", DateTimeUtil.formatDate(date, DateFormat.SHORT_DASH));
        dataByColumn.put("B", DateTimeUtil.formatTime(timeIn, TimeFormat.HOUR_MINUTE));
        dataByColumn.put("C", DateTimeUtil.formatTime(timeOut, TimeFormat.HOUR_MINUTE));
        dataByColumn.put("D", DateTimeUtil.formatTime(timeIn, TimeFormat.MERIDIEM));
        return dataByColumn;
    }
}