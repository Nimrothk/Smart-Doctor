package com.example.user.research;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class SOS extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        toolbar();

    }

    public void toolbar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.SOS);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}