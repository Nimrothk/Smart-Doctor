package com.example.user.research;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class rating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        toolbar();
    }
    public void toolbar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.Rating);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        Intent i = new Intent(rating.this, Home.class);
        startActivity(i);
        finish();
        Toast.makeText(this,"Home",Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}

