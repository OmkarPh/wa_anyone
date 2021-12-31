package com.omkarph.wa_anyone.db;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Entry {
    private final String PHONE;
    private final String DATE_TIME;

    public Entry(String PHONE, String DATE_TIME) {
        this.PHONE = PHONE;
        this.DATE_TIME = DATE_TIME;
    }

    public Entry(String PHONE) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Current time => "+c.getTime());
        Log.d("historyDB", "time: "+c.getTime());
        String formattedDate = df.format(c.getTime());
        Log.d("historyDB", "Formatted time: "+formattedDate);

        this.PHONE = PHONE;
        this.DATE_TIME = formattedDate;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getDATE_TIME() {
        return DATE_TIME;
    }
}
