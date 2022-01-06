package com.secretdevbd.xian.pigeoninfinityapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.secretdevbd.xian.pigeoninfinityapp.Activities.SplashScreenActivity;
import com.secretdevbd.xian.pigeoninfinityapp.Functions.SharedPreferenceClass;
import com.secretdevbd.xian.pigeoninfinityapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    private Button signOut;
    private FirebaseAuth mAuth;
    private SharedPreferenceClass SP;

    public profileFragment() {
        // Required empty public constructor
    }

    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        signOut = view.findViewById(R.id.signOut);
        mAuth = FirebaseAuth.getInstance();
        SP = new SharedPreferenceClass(getContext());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SP.setLoginUserID("#");
                getActivity().finish();
                startActivity(new Intent(getContext(), SplashScreenActivity.class));
            }
        });

        return view;
    }
}