package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.secretdevbd.xian.pigeoninfinityapp.Functions.SharedPreferenceClass;

import com.secretdevbd.xian.pigeoninfinityapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferenceClass SP;
    String TAG = "XIAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SP = new SharedPreferenceClass(this);
        delayLoad();

        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        //finish();
    }

    private void delayLoad(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.i(TAG, SP.getLoginUserID());
                if (SP.getLoginUserID() == "#"){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }, 1500);   //5 seconds
    }
}