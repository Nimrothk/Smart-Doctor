package com.example.user.research;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class summary extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_reading adapter_reading;

   private DatabaseReference databaseReference;
   private List<Model_reading> muploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        databaseReference= FirebaseDatabase.getInstance().getReference("scan");

        recyclerView=findViewById(R.id.recyclerViewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        muploads=new ArrayList<Model_reading>();
        adapter_reading=new Adapter_reading(summary.this,muploads);
        recyclerView.setAdapter(adapter_reading);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                muploads.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Model_reading mr=dataSnapshot.getValue(Model_reading.class);
                    muploads.add(mr);

                  //  Toast.makeText(summary.this, ""+mr.getHum(), Toast.LENGTH_SHORT).show();

                }
                adapter_reading.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        toolbar();
    }

    public void toolbar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.Summary);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        Intent i = new Intent(summary.this, Home.class);
        startActivity(i);
        finish();
        Toast.makeText(this,"Home",Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}

