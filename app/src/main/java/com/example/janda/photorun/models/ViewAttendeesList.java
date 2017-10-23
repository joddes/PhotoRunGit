package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.ViewUserList;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.ViewPhotorunList;

import com.example.janda.photorun.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.janda.photorun.Chat.ViewUserList.USER_ID;

/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class ViewAttendeesList extends AppCompatActivity {


    private TextView toolbar_Textview;


    DatabaseReference databasePhotorun;

    ListView listViewUsers;
    List<String> followers;

    private String uebergebeneID;

    private String clickedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_runs);

        Intent intent = getIntent();

        uebergebeneID = intent.getStringExtra(ViewProfile.USER);


        //Liste von Photoruns anzeigen


        databasePhotorun = FirebaseDatabase.getInstance().getReference("User").child(uebergebeneID).child("followers");
        listViewUsers = (ListView) findViewById(R.id.PhotoRunList);

        //list to store Photoruns
        followers = new ArrayList<>();


        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Followers");

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                clickedUser = followers.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);

                //putting artist name and id to intent
                intent.putExtra(USER_ID, clickedUser);
                //starting the activity with intent
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ViewAttendeesList.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(intent, options.toBundle());

            }
        });

        //Bottom Toolbar-----------------------------------------------------------
        final ImageButton profileBtn = (ImageButton) findViewById(R.id.Profilbtn);
        final ImageButton runBtn = (ImageButton) findViewById(R.id.Photorunbtn);
        final ImageButton searchBtn = (ImageButton) findViewById(R.id.Suchbtn);
        final ImageButton mapBtn = (ImageButton) findViewById(R.id.Mapbtn);
        findViewById(R.id.menu4).setBackgroundResource(R.color.white);
        profileBtn.setBackgroundResource(R.drawable.go_profile_icon_orange);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewAttendeesList.this, ViewUserList.class);
                findViewById(R.id.menu1).setBackgroundResource(R.color.white);
                searchBtn.setBackgroundResource(R.drawable.messenger_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ViewAttendeesList.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewAttendeesList.this, ViewPhotorunList.class);
                findViewById(R.id.menu3).setBackgroundResource(R.color.white);
                runBtn.setBackgroundResource(R.drawable.go_run_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ViewAttendeesList.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewAttendeesList.this, ProfileActivity.class);

                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ViewAttendeesList.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());

            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }


    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);






        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                followers.clear();


                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String user = userSnapshot.getKey();


                    followers.add(user);
                    findViewById(R.id.progressBar).setVisibility(View.GONE);

                }

                ViewAttendeesListAdapter adapter = new ViewAttendeesListAdapter(ViewAttendeesList.this, followers);

                listViewUsers.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

