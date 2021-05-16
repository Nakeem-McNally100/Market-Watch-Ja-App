package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NewpaperScreen extends AppCompatActivity implements  AppBarLayout.OnOffsetChangedListener {

    private ImageView imageView;
    private TextView apptitle, appsubtitle, date, time, title;
    private boolean ishidetoolbarview = false;
    private FrameLayout datebehaviour;
    private LinearLayout titleappbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String murl,mimg, mtitle, mdate, msource, mauthor;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpaper_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        datebehaviour = findViewById(R.id.date_behavior);
        titleappbar = findViewById(R.id.title_appbar);
        imageView = findViewById(R.id.backdrop);
        appsubtitle = findViewById(R.id.subtitle_on_appbar);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);

        Intent intent = getIntent();
        murl = intent.getStringExtra("url");
        mimg = intent.getStringExtra("img");
        mtitle = intent.getStringExtra("title");
        mdate = intent.getStringExtra("date");
        msource = intent.getStringExtra("source");
        mauthor = intent.getStringExtra("author");


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());
        Glide.with(this).load(mimg).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);

        apptitle.setText(msource);
        appsubtitle.setText(murl);
        date.setText(mdate);
        title.setText(mtitle);

        String author = null;
        if(mauthor !=null || mauthor!=""){
            mauthor = '\u2022' +mauthor;
        }else{
            author = "";
        }

        time.setText(msource + author+ "\u2022" + Utils.DateToTimeFormat(mdate));
        initWebView(murl);
    }

    private void initWebView(String url){
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed(){

    super.onBackPressed();
    supportFinishAfterTransition();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset)/(float)maxScroll;

        if(percentage == 1f && ishidetoolbarview){
            datebehaviour.setVisibility(View.GONE);
            titleappbar.setVisibility(View.VISIBLE);
            ishidetoolbarview = !ishidetoolbarview;

        }else if(percentage < 1f && ishidetoolbarview){
            datebehaviour.setVisibility(View.VISIBLE);
            titleappbar.setVisibility(View.GONE);
            ishidetoolbarview = !ishidetoolbarview;

        }
    }
}