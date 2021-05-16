package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.capstoneapp.api.ApiInterface;
import com.example.capstoneapp.api.Client;
import com.example.capstoneapp.newsmodels.Articles;
import com.example.capstoneapp.newsmodels.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedScreen extends AppCompatActivity {



    public static final String API_KEY ="20b486e870414b6087e25487f9245f37";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Articles>articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = NewsFeedScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_screen);
        getSupportActionBar().setTitle("Market Watch Jamaica");
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(NewsFeedScreen.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        // MY API KEY:  20b486e870414b6087e25487f9245f37

        ///Initialise and assign variables

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set news feed button selected
        bottomNavigationView.setSelectedItemId(R.id.newsfeedbtn);

        // perform itemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.searchbtn:
                        startActivity(new Intent( getApplicationContext(),SearchScreenV2.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profilebtn:
                        startActivity(new Intent( getApplicationContext(),ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.newsfeedbtn:
                        return true;
                }
                return false;
            }
        });


            LoadJson();
    }


    public void LoadJson(){

        ApiInterface apiInterface = Client.getClient().create(ApiInterface.class);
        Call<News> call;
        String country = Utils.getCountry();
        call = apiInterface.getNews(country,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    if(articles.isEmpty()){
                        articles.clear();

                    }
                    articles = response.body().getArticles();
                    adapter = new Adapter(articles, NewsFeedScreen.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListener();

                }else{
                    Toast.makeText(NewsFeedScreen.this,"Opps, No Result Aquired",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    private void initListener(){
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NewsFeedScreen.this, NewpaperScreen.class);
                Articles article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author",article.getAuthor());
                startActivity(intent);
            }
        });
    }

}