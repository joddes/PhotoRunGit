package com.example.janda.photorun.models;

/**
 * Created by Tim Seemann on 12.10.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.Users;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.CreateRun;
import com.example.janda.photorun.Photorun.ViewPhotoRuns;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.models.ViewAttendeesListAdapter;

import com.example.janda.photorun.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim Seemann on 09.10.2017.
 */



public class ViewTeilnehmerliste extends AppCompatActivity {

    private TextView toolbar_Textview;


    DatabaseReference databasePhotorun;

    ListView listViewUsers;
    List<String> users;

    private String uebergebeneID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_runs);

        Intent intent = getIntent();

        uebergebeneID = intent.getStringExtra(ViewPhotorunList.PHOTORUN_ID);


        //Liste von Photoruns anzeigen


        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun").child(uebergebeneID).child("attendees");
        listViewUsers = (ListView) findViewById(R.id.PhotoRunList);

        //list to store Photoruns
        users = new ArrayList<>();


        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Anwesende Teilnehmer");

        //Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Diese Teilnehmer ware alle bei dem Photowalk anwesend!;)");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);
        //back
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewTeilnehmerliste.this, ViewPhotorunList.class);

                finish();

                startActivity(myIntent);
            }
        });

        //Help Button, muss auf jeder Seite sein
        final ImageButton helpBtn = (ImageButton) findViewById(R.id.help_icon);
        final Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
        final Animation slide_right_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_out);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            int var = 1;
            public void onClick(View view) {
                if(var==1) {
                    findViewById(R.id.help_seite).startAnimation(slide_left);
                    findViewById(R.id.help_seite).setVisibility(View.VISIBLE);
                    findViewById(R.id.help_icon).setBackgroundResource(R.drawable.cancel_icon);
                    var = 0;
                }else{
                    findViewById(R.id.help_seite).startAnimation(slide_right_out);
                    findViewById(R.id.help_seite).setVisibility(View.GONE);
                    findViewById(R.id.help_icon).setBackgroundResource(R.drawable.help_icon);
                    var = 1;
                }
            }
        });

        //Bottom Toolbar-----------------------------------------------------------
        final ImageButton profileBtn = (ImageButton) findViewById(R.id.Profilbtn);
        final ImageButton runBtn = (ImageButton) findViewById(R.id.Photorunbtn);
        final ImageButton searchBtn = (ImageButton) findViewById(R.id.Suchbtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewTeilnehmerliste.this, Users.class);
                finish();
                startActivity(myIntent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewTeilnehmerliste.this, ViewPhotorunList.class);
                finish();
                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewTeilnehmerliste.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);

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

                users.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String user = userSnapshot.getKey();


                    users.add(user);
                    findViewById(R.id.progressBar).setVisibility(View.GONE);

                }

                ViewAttendeesListAdapter adapter = new ViewAttendeesListAdapter(ViewTeilnehmerliste.this, users);

                listViewUsers.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

