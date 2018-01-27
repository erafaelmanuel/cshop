package io.ermdev.cshop.commons;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    final Calendar CALENDAR = Calendar.getInstance();
    private Date date;

    public DateHelper() {
        date = new Date();
    }

    public void setTimeNow() {
        CALENDAR.setTime(new Timestamp(CALENDAR.getTime().getTime()));
        date = new Date(CALENDAR.getTime().getTime());
    }

    public void addTimeInMinute(final int time) {
        CALENDAR.set(Calendar.MINUTE, time);
        date = new Date(CALENDAR.getTime().getTime());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static int dayInMinute() {
        return 60 * 24;
    }

    public static int weekInMinute() {
        return 60 * 24 * 7;
    }
}
