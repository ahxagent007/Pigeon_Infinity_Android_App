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
        //Log.i(TAG, "Current TIME : "+currentDate);
        return currentDate;
    }

    public String getTimeDifferenceEnd(Date EndTime){
        Date currentTime = getCurrentTime();

        // in milliseconds
        long difference_In_Time
                = EndTime.getTime() - currentTime.getTime();

        long difference_In_Seconds
                = (difference_In_Time
                / 1000)
                % 60;

        long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60))
                % 60;

        long difference_In_Hours
                = (difference_In_Time
                / (1000 * 60 * 60))
                % 24;

        /*long difference_In_Years
                = (difference_In_Time
                / (1000l * 60 * 60 * 24 * 365));*/

        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;

        if(difference_In_Days==0 && difference_In_Hours==0 && difference_In_Minutes==0){
            return difference_In_Seconds+" S";
        }else if(difference_In_Days==0 && difference_In_Hours==0){
            return difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }else if(difference_In_Days==0){
            return difference_In_Hours+" Hours "+difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }else if(difference_In_Seconds<0){
            return "AUCTION ENDED !!";
        }else{
            return difference_In_Days+" Days "+difference_In_Hours+" Hours "+difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }
    }

    public String getTimeDifferenceStart(Date StartTime){
        Date currentTime = getCurrentTime();

        // in milliseconds
        long difference_In_Time
                = currentTime.getTime() - StartTime.getTime();

        long difference_In_Seconds
                = (difference_In_Time
                / 1000)
                % 60;

        long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60))
                % 60;

        long difference_In_Hours
                = (difference_In_Time
                / (1000 * 60 * 60))
                % 24;

        /*long difference_In_Years
                = (difference_In_Time
                / (1000l * 60 * 60 * 24 * 365));*/

        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;

        if(difference_In_Days==0 && difference_In_Hours==0 && difference_In_Minutes==0){
            return difference_In_Seconds+" Seconds";
        }else if(difference_In_Days==0 && difference_In_Hours==0){
            return difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }else if(difference_In_Days==0){
            return difference_In_Hours+" Hours "+difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }else{
            return difference_In_Days+" Days "+difference_In_Hours+" Hours "+difference_In_Minutes+" Minutes "+difference_In_Seconds+" Seconds";
        }
    }
}
