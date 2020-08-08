package com.example.user.research;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private CircleImageView navImageView;
    private TextView navProfileUsername;
    String currentuserId;
    private RecyclerView postlist;
    private static final String TAG = "MapActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentuserId = mAuth.getCurrentUser().getUid();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

     //   postlist = (RecyclerView) findViewById(R.id.recycler_view);
      //  postlist.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
//        postlist.setLayoutManager(linearLayoutManager);


        navImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_profileImage);

        navProfileUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_full_name);

        UserRef.child(currentuserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("username")) {
                        String Fullname = dataSnapshot.child("username").getValue().toString();
                        navProfileUsername.setText(Fullname);
                    }
                    if (dataSnapshot.hasChild("ProfileImage")) {
                        String image = dataSnapshot.child("ProfileImage").getValue().toString();

                        Picasso.with(Home.this).load(image).placeholder(R.drawable.images).into(navImageView);
                    } else {
                        Toast.makeText(Home.this, "Profile name do not exists..", Toast.LENGTH_SHORT).show();
                        // SendUserToLoginActivity();
                        //sendUsersetupProfile();


                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void DisplayAllUsersPosts() {

       // Query sortPostIndesendingOrder = PostsRef.orderByChild("counter");
        UpdateUserStatus("offline");
    }


    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        View mview;
        String currentUserID;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
            }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            SendUserToLoginActivity();

        } else {

            checkUserExistence();
        }
    }

    private void sendUsersetupProfile() {
        Intent intentlogin = new Intent(this, ProfileSetupActivity.class);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentlogin);
        finish();
    }

    private void checkUserExistence() {
        final String Current_User_ID = mAuth.getCurrentUser().getUid();
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(Current_User_ID)) {
                    sendUsersetupProfile();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void SendUserToLoginActivity() {
        Intent intentlogin = new Intent(this, LoginActivity.class);
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentlogin);
        finish();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void UpdateUserStatus(String state) {

        String SaveCurretdate, SaveCurrentTime;

        Calendar calForeDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        SaveCurretdate = currentDate.format(calForeDate.getTime());

        Calendar calForetime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm a");
        SaveCurrentTime = currenttime.format(calForetime.getTime());


        Map currentstatus = new HashMap();
        currentstatus.put("time", SaveCurrentTime);
        currentstatus.put("Date", SaveCurretdate);
        currentstatus.put("Type", state);

        UserRef.child(currentuserId).child("UserState").updateChildren(currentstatus);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this,chat.class);
            startActivity(intent);
            Toast.makeText(this, "Home", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_summary) {
            Intent intent = new Intent(Home.this, summary.class);
            startActivity(intent);
            Toast.makeText(this, "Summary", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_sos) {
            Intent intent = new Intent(this, SOS.class);
            startActivity(intent);
            Toast.makeText(this, "SOS", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_rating) {
            Intent intent = new Intent(Home.this, rating.class);
            startActivity(intent);
            Toast.makeText(this, "Rating", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            Toast.makeText(this, "About", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_settting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            Toast.makeText(this, "About", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_logOut) {
            mAuth.signOut();
            UpdateUserStatus("offline");
            SendUserToLoginActivity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
