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

public class EditAccount extends AppCompatActivity {


    private Button editcontinuebtn;
    private EditText editfullName;
    private EditText editemail;
    private EditText editpassword;
    private  EditText editconfirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        editcontinuebtn = (Button) findViewById(R.id.editbtn);
        editfullName = (EditText) findViewById(R.id.editfullname);
        editemail = (EditText) findViewById(R.id.editemail);
        editpassword = (EditText) findViewById(R.id.editpassword);
        editconfirmpassword = (EditText) findViewById(R.id.editcomfirmpassword);


        editcontinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenApp();
            }
        });
    }




    public void OpenApp(){
        //Intent intent = new Intent(this, ResultScreen.class);
        //startActivity(intent);
        String str1 = editpassword.getText().toString();
        String str2 = editconfirmpassword.getText().toString();

        if(str1.equals(str2)) {

            Random randowNum = new Random();
            String userid = "User" + String.valueOf(randowNum.nextInt(2000));
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(EditAccount.this);
            String url = "http://192.168.1.7:5000/user/" + editemail.getText().toString() + "/" + userid + "/" + editpassword.getText().toString() + "/" + editfullName.getText().toString();
            //String url ="https://10.0.2.2:5000/user/"+logInEmail.getText().toString();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //  textView.setText("Response is: "+ response.substring(0,500));

                            editemail.setText("");
                            editfullName.setText("");
                            editpassword.setText("");
                            editconfirmpassword.setText("");
                            OpenNewsFeed();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(EditAccount.this, "Oops didn't work, try again", Toast.LENGTH_SHORT).show();

                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }else{
            Toast.makeText(EditAccount.this, "Oops didn't work, check your password", Toast.LENGTH_SHORT).show();
        }
    }


    public void OpenNewsFeed(){
        Intent intent = new Intent(this, NewsFeedScreen.class);
        startActivity(intent);

    }



}