package com.secretdevbd.xian.pigeoninfinityapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.secretdevbd.xian.pigeoninfinityapp.Functions.SharedPreferenceClass;
import com.secretdevbd.xian.pigeoninfinityapp.R;

import java.util.concurrent.TimeUnit;

public class LoginSMSActivity extends AppCompatActivity {

    String TAG = "XIAN";

    EditText ET_Code;
    Button btn_done;
    ProgressBar PB_loadingSMS;

    FirebaseAuth mAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String verificationCode;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_s_m_s);

        ET_Code = findViewById(R.id.ET_Code);
        btn_done = findViewById(R.id.btn_done);
        PB_loadingSMS = findViewById(R.id.PB_loadingSMS);

        mAuth = FirebaseAuth.getInstance();

        mobileNumber = getIntent().getStringExtra("MOBILE_NUMBER");

        FirebasePhoneRegistration(mobileNumber);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ET_Code.getText().toString().trim();

                if (code.isEmpty() || code.length()<6){
                    ET_Code.setError("Enter Code...");
                    ET_Code.requestFocus();
                    return;
                }

                PB_loadingSMS.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void FirebasePhoneRegistration(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential);
            verificationCode = credential.getSmsCode();
            if(verificationCode != null){
                PB_loadingSMS.setVisibility(View.VISIBLE);
                verifyCode(verificationCode);
            }
            //signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Log.i(TAG, "Exception: "+e);
                // Invalid request
                Toast.makeText(getApplicationContext(), "Please Try again with another phone number", Toast.LENGTH_LONG).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.i(TAG, "Exception: "+e);
                Toast.makeText(getApplicationContext(), "Please Contact with admin.", Toast.LENGTH_LONG).show();
            }

            // Show a message and update the UI
        }


    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            new SharedPreferenceClass(LoginSMSActivity.this).setLoginUserID(user.getUid());

                            //Create new User
                            createUserOnFirebase(user.getUid(), mobileNumber);
                            // Update UI
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_LONG).show();
                                PB_loadingSMS.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
    }

    public void createUserOnFirebase(String user_id, String mobileNumber){

    }
}