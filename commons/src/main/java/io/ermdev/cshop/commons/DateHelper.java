package io.ermdev.cshop.commons;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateHelper {

    public static final int DAY_IN_MINUTE = 60 * 24;
    public static final int WEEK_IN_MINUTE = 60 * 24 * 7;

    public final Calendar CALENDAR = Calendar.getInstance();

    private Date date;

    public DateHelper() {
        date = new Date();
    }

    public DateHelper setTimeNow() {
        CALENDAR.setTime(new Timestamp(CALENDAR.getTime().getTime()));
        date = new Date(CALENDAR.getTime().getTime());
        return this;
    }

    public DateHelper addTimeInMinute(final int time) {
        CALENDAR.set(Calendar.MINUTE, time);
        date = new Date(CALENDAR.getTime().getTime());
        return this;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
