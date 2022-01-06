package com.secretdevbd.xian.pigeoninfinityapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.secretdevbd.xian.pigeoninfinityapp.R;


public class HomeFragment extends Fragment {

    TextView TV_heading;
    String HEADING_TEXT;
    String url_website, url_youtube;

    ImageButton btn_website, btn_youtube;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INITIALIZE PARAMETERS
        HEADING_TEXT = "Highest Quality Pigeons for All";
        url_website =  "http://www.pigeoninfinity.com";
        url_youtube = "https://www.youtube.com/channel/UCdd4e1LKwxP23O_FmxhGgnQ";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_website = view.findViewById(R.id.btn_website);
        btn_youtube = view.findViewById(R.id.btn_youtube);

        TV_heading = view.findViewById(R.id.TV_heading);
        TV_heading.setText(HEADING_TEXT.toUpperCase());


        TextPaint paint = TV_heading.getPaint();
        float width = paint.measureText(HEADING_TEXT);

        Shader textShader = new LinearGradient(0, 0, width, TV_heading.getTextSize(),
                new int[]{
                        Color.parseColor("#E8E8E8"),
                        Color.parseColor("#FFFFFF"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        TV_heading.getPaint().setShader(textShader);

        btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_website));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_youtube));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        return view;
    }

    DatabaseReference databaseRef;

    public void checkLoginStatus(){
        databaseRef = FirebaseDatabase.getInstance().getReference("PI_User");
    }
}