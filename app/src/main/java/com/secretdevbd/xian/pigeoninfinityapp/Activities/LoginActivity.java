package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.secretdevbd.xian.pigeoninfinityapp.R;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    String TAG = "XIAN";

    EditText ET_countryCode, ET_mobileNumber;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_countryCode = findViewById(R.id.ET_countryCode);
        ET_mobileNumber = findViewById(R.id.ET_mobileNumber);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country_code = ET_countryCode.getText().toString();
                String mobile_number = ET_mobileNumber.getText().toString();

                if (country_code.equalsIgnoreCase("+88") || mobile_number.equalsIgnoreCase("+91")){
                    if (!mobile_number.equalsIgnoreCase("")){
                        //next Activity
                        Intent at = new Intent(getApplicationContext(), LoginSMSActivity.class);
                        at.putExtra("MOBILE_NUMBER", country_code+mobile_number);
                        startActivity(at);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter a valid number", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Only Bangladesh and Indian number allowed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}