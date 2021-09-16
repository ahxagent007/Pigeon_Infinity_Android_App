package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.secretdevbd.xian.pigeoninfinityapp.R;

public class SinglePigeonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pigeon);

        int AuctionID = getIntent().getIntExtra("AUCTION_ID", 0);
        int PigeonID = getIntent().getIntExtra("PIGEON_ID", 0);

    }
}