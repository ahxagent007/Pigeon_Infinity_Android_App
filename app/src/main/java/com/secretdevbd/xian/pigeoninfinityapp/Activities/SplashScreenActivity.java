package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.secretdevbd.xian.pigeoninfinityapp.Functions.SharedPreferenceClass;

import com.secretdevbd.xian.pigeoninfinityapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferenceClass SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

         SP = new SharedPreferenceClass(this);
        delayLoad();

    }

    private void delayLoad(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

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