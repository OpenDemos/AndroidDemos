package com.apps4med.helloapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by iskitsas on 23/10/2014.
 */
public class DateHelper {
    public String getNowDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }
}
