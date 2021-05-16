package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchScreenV2 extends AppCompatActivity {

    ListView searchlist1;
    ArrayAdapter<String> adapter1;
    //SearchView search1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen_v2);
        getSupportActionBar().setTitle("Search");
        ///Initialise and assign variables
       // search1 = (SearchView) findViewById(R.id.mysearchbar);
        searchlist1 = (ListView) findViewById(R.id.mylist);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ArrayList<String> CompanyNames = new ArrayList<>();
        CompanyNames.addAll(Arrays.asList(getResources().getStringArray(R.array.mycompanies)));


        adapter1 = new ArrayAdapter<String>(
                SearchScreenV2.this,
                android.R.layout.simple_list_item_1,
                CompanyNames
        );

        searchlist1.setAdapter(adapter1);

        searchlist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myresults = new Intent(SearchScreenV2.this, ResultScreen.class);
                myresults.putExtra("CompanyName", searchlist1.getItemAtPosition(i).toString());
                startActivity(myresults);

            }
        });







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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.searchcompany);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}








