package com.secretdevbd.xian.pigeoninfinityapp.Functions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceClass {

    Context context;

    public SharedPreferenceClass(Context context){
        this.context = context;
    }

    public void setLoginUserID(String user){
        SharedPreferences sharedPref = context.getSharedPreferences("APP", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("LOGIN_USER", user);
        editor.apply();
    }

    public String getLoginUserID(){
        SharedPreferences sharedPref = context.getSharedPreferences("APP", context.MODE_PRIVATE);
        String user = sharedPref.getString("LOGIN_USER", "#");
        return user;
    }
}
