package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button continuebtn;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private  EditText confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        continuebtn = (Button) findViewById(R.id.conbtn);
        fullName = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.comfirmpassword);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenApp();
                //email.getText().toString();
                //Toast.makeText(MainActivity.this, "you typed" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void OpenApp(){
        //Intent intent = new Intent(this, ResultScreen.class);
        //startActivity(intent);
        String str1 = password.getText().toString();
        String str2 = confirmpassword.getText().toString();

         if(str1.equals(str2)) {

            Random randowNum = new Random();
            String userid = "User" + String.valueOf(randowNum.nextInt(2000));
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "http://192.168.1.7:5000/user/" + email.getText().toString() + "/" + userid + "/" + password.getText().toString() + "/" + fullName.getText().toString();
            //String url ="https://10.0.2.2:5000/user/"+logInEmail.getText().toString();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //  textView.setText("Response is: "+ response.substring(0,500));
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            email.setText("");
                            fullName.setText("");
                            password.setText("");
                            confirmpassword.setText("");
                            OpenNewsFeed();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "Oops didn't work, try again", Toast.LENGTH_SHORT).show();

                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }else{
            Toast.makeText(MainActivity.this, "Oops didn't work, check your password", Toast.LENGTH_SHORT).show();
        }
    }


    public void OpenNewsFeed(){
        Intent intent = new Intent(this, NewsFeedScreen.class);
        startActivity(intent);

    }

}