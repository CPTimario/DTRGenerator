package app.dtrgenerator.model;

import java.time.LocalTime;
import java.util.Date;

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
        return date != null && timeIn != null && timeOut != null;
    }

    @Override
    public int compareTo(Session compareSession) {
        int result = this.getDate().compareTo(compareSession.getDate());
        return result != 0 ? result : this.getTimeIn().compareTo(compareSession.getTimeIn());
    }
}