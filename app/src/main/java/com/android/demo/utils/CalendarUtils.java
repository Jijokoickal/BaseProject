package com.android.app.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 06-08-2015.
 */
public class CalendarUtils {

    public long getMillis(final Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    public Calendar dateToCalendar(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return calendar;
    }

}
