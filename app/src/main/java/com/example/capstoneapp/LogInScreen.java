package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LogInScreen extends AppCompatActivity {

    private Button createAccount;
    private Button logIn;
    private EditText logInEmail;
    private EditText logInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        createAccount = (Button) findViewById(R.id.createAccount);
        logIn = (Button) findViewById(R.id.continueloginbtn);
        logInEmail = (EditText) findViewById(R.id.loginemail);
        logInPassword = (EditText) findViewById(R.id.loginpassword);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCreateAccount();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // #3A3939 nice dark grey
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(LogInScreen.this);
                String url ="http://192.168.1.7:5000/user/"+logInEmail.getText().toString()+"/userlogin/passwrdlog/fullnamelog";
                //String url ="https://10.0.2.2:5000/user/"+logInEmail.getText().toString();
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                              //  textView.setText("Response is: "+ response.substring(0,500));
                                 Toast.makeText(LogInScreen.this, response, Toast.LENGTH_SHORT).show();
                                logInEmail.setText("");
                                logInPassword.setText("");
                                 OpenNewsFeed();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LogInScreen.this, "Oops didn't work, try again later @ http://192.168.1.7:5000/user/"+logInEmail.getText().toString() , Toast.LENGTH_SHORT).show();
                     //   textView.setText("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

    }

    public void OpenCreateAccount(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void OpenNewsFeed(){
        Intent intent = new Intent(this, NewsFeedScreen.class);
        startActivity(intent);

    }

}