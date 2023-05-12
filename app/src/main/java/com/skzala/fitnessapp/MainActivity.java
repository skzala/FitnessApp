package com.skzala.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Hide the navigation bar and make the app full-screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                      );

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile Activity");
        actionBar.setElevation(16);


        // Initialize and set bottom navigation listener
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
        actionBar.setTitle("Home");

        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        navigationView.setItemTextColor(colorStateList);

        // Set initial fragment to HomeFragment
        homefrags homeFragment = new homefrags();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        navigationView.setSelectedItemId(R.id.nav_home);
        fragmentTransaction.replace(R.id.content, homeFragment, "");
        fragmentTransaction.commit();
    }

    // Handle bottom navigation item selection
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_achiv:
                    // Replace current fragment with AchievementsFragment
                    actionBar.setTitle("Achivements");
                    achivementsfrag achievementsFragment = new achivementsfrag();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, achievementsFragment, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_socials:
                    // Replace current fragment with SocialFragment
                    actionBar.setTitle("Socials");
                    socialfrag socialFragment = new socialfrag();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, socialFragment);
                    fragmentTransaction1.commit();
                    return true;

                case R.id.nav_home:
                    // Replace current fragment with HomeFragment
                    actionBar.setTitle("Home");
                    homefrags homeFragment = new homefrags();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.content, homeFragment, "");
                    fragmentTransaction2.commit();
                    return true;

                case R.id.nav_exercise:
                    // Replace current fragment with ExerciseFragment
                    actionBar.setTitle("Exercise");
                    exercisefrags exerciseFragment = new exercisefrags();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.content, exerciseFragment, "");
                    fragmentTransaction3.commit();
                    return true;

                case R.id.nav_settings:
                    // Replace current fragment with SettingsFragment
                    actionBar.setTitle("Settings");
                    settingsfrag settingsFragment = new settingsfrag();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.content, settingsFragment, "");
                    fragmentTransaction4.commit();
                    return true;
            }
            return false;
        }
    };
}
