package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class ViewAttendeesList extends AppCompatActivity {


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


        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun").child(uebergebeneID).child("participants");
        listViewUsers = (ListView) findViewById(R.id.PhotoRunList);

        //list to store Photoruns
        users = new ArrayList<>();


        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Alle Anmeldungen");
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

                ViewAttendeesListAdapter adapter = new ViewAttendeesListAdapter(ViewAttendeesList.this, users);

                listViewUsers.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

