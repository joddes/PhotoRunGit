package com.example.janda.photorun.Photorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.janda.photorun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PersonalPhotorunList extends AppCompatActivity {


    private TextView toolbar_Textview;


    DatabaseReference databasePhotorunUser;
    FirebaseAuth mAuth;

    ListView listViewPhotoruns;
    List<String> photoruns;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_photorun_list);

        Intent intent = getIntent();

        //ubID = intent.getStringExtra(ViewPhotorunList.PHOTORUN_ID);


        //Liste von Photoruns anzeigen

        userID = mAuth.getInstance().getCurrentUser().getUid();

        databasePhotorunUser = FirebaseDatabase.getInstance().getReference("User").child(userID).child("participatedRuns");
        listViewPhotoruns = (ListView) findViewById(R.id.enrolledListView);

        //list to store Photoruns
        photoruns = new ArrayList<>();


        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Deine Photowalks");
    }


    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);


        databasePhotorunUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoruns.clear();

                for (DataSnapshot photorunSnapshot : dataSnapshot.getChildren()) {
                    String photorun = photorunSnapshot.getKey();
                    photoruns.add(photorun);
                }
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                PersonalListadapter adapter = new PersonalListadapter(PersonalPhotorunList.this, photoruns);

                listViewPhotoruns.setAdapter(adapter);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

