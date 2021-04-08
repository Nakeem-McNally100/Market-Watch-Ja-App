package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfilePage extends AppCompatActivity {


    Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        ///Initialise and assign variables
        logOut = (Button) findViewById(R.id.logOutprofilebtn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set profile button selected
        bottomNavigationView.setSelectedItemId(R.id.profilebtn);

        // perform itemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.searchbtn:
                        startActivity(new Intent( getApplicationContext(),SearchScreen.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profilebtn:
                        return true;

                    case R.id.newsfeedbtn:
                        startActivity(new Intent( getApplicationContext(),NewsFeedScreen.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilePage.this, "Logging Out, come back later.", Toast.LENGTH_SHORT).show();
                getOut();
            }
        });
    }


    public void getOut(){
        Intent intent = new Intent(this,LogInScreen.class);
        startActivity(intent);
    }
}