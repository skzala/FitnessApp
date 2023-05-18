package com.skzala.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    BottomNavigationView navigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile Activity");
        actionBar.setElevation(16);

        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

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
                case R.id.nav_cal:
                    // Replace current fragment with AchievementsFragment
                    actionBar.setTitle("Cal Counter");
                    counterFrag counterfrag = new counterFrag();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, counterfrag, "");
                    fragmentTransaction.commit();
                    return true;

                case R.id.nav_track:
                    // Replace current fragment with SocialFragment
                    actionBar.setTitle("Tracker");
                    TrackingFrag trackingFrag = new TrackingFrag();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.content, trackingFrag);
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

                case R.id.nav_logout:

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Don't quit now! Stay motivated and continue your fitness journey.");
                    builder.setTitle("Logout?");
                    builder.setPositiveButton("Nah,I GIVE Up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Log the user out.
                           SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loggedin", "0");
                            editor.apply();
                            startActivity(new Intent(MainActivity.this,loginscreen.class));
                        }
                    });
                    builder.setNegativeButton("Keep Pushing", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                   // AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Logout? Are you sure?");

                    return true;
            }
            return false;
        }
    };
}
