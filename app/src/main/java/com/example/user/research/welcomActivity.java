package com.example.user.research;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class welcomActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable(){
            public void run(){

                Intent homeIntent = new Intent(welcomActivity.this, LoginActivity.class);
                startActivity(homeIntent);

                finish();

            }
        },SPLASH_TIME_OUT);



    }
}
