package com.secretdevbd.xian.pigeoninfinityapp.Functions;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeSettings {
    String TAG = "XIAN";
    public Date convertStringDateToDate(String date){
        Date date_obj = null;
        try {
            TimeZone tz = TimeZone.getTimeZone("Asia/Dhaka");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            simpleDateFormat.setTimeZone(tz);
            date_obj = simpleDateFormat.parse(date);

        }catch (Exception e){
            Log.e(TAG,""+e);
        }

        return date_obj;

    }

    public Date getCurrentTime(){
        TimeZone tz = TimeZone.getTimeZone("Asia/Dhaka");
        Date currentDate = Calendar.getInstance(tz).getTime();
        Log.i(TAG, "Current TIME : "+currentDate);
        return currentDate;
    }
}
