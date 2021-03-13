package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);


        ///Initialise and assign variables

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set search button selected
        bottomNavigationView.setSelectedItemId(R.id.searchbtn);

        // perform itemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.searchbtn:
                        return true;

                    case R.id.profilebtn:
                        startActivity(new Intent( getApplicationContext(),ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.newsfeedbtn:
                        startActivity(new Intent( getApplicationContext(),NewsFeedScreen.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




    }
}