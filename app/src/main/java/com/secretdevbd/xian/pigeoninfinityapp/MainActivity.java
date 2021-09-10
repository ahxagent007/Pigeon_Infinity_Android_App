package com.secretdevbd.xian.pigeoninfinityapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    BottomNavigationView bottom_navigatin_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new HomeFragment());

        //getting bottom navigation view and attaching the listener
        bottom_navigatin_view = findViewById(R.id.bottom_navigatin_view);
        bottom_navigatin_view.setOnItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.homeFragment:
                fragment = new HomeFragment();
                break;

            case R.id.actionListFragment:
                fragment = new AuctionListFragment();
                break;

            case R.id.piCareFragment:
                fragment = new piCareFragment();
                break;

            case R.id.profileFragment:
                fragment = new profileFragment();
                break;
        }

        return loadFragment(fragment);
    }


}