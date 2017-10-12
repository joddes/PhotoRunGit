package com.example.janda.photorun.Photorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import android.widget.AdapterView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.Users;
import com.example.janda.photorun.GoogleMaps.MapsActivity;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.example.janda.photorun.models.ViewAttendeesList;
import com.example.janda.photorun.models.ViewAttendeesListAdapter;
import com.example.janda.photorun.models.ViewTeilnehmerliste;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewSinglePhotoRun extends AppCompatActivity implements View.OnClickListener  {

    //implements View.OnClickListener

    private TextView title_Textview, Textview_date, date_Textview, startpoint_Textview, endpoint_Textview, starttime_Textview, duration_Textview, participants_Textview, description_Textview,toolbar_Textview;

    private FloatingActionButton joinRunButton, attendButton;

    private AppCompatButton viewAtt;

    private String date, description, end_point, estimated_duration, max_participators, start_point, starting_time, title;

    private DatabaseReference mDatabase, joinDatabase, userDatabase;

    private FirebaseAuth mAuth;

    private Button test;

    DatabaseReference Test;

    String photorun_id;

    private long curPart, maxPart;

    private String maxPartBuffer;

    DatabaseReference databasePhotorun;

    ListView listViewUsers;
    List<String> users;

    public static final String PHOTORUN_STARTPOINT = "com.example.janda.photorun.models.start_point";
    public static final String PHOTORUN_ENDPOINT = "com.example.janda.photorun.models.end_point";
    public static final String PHOTORUN_TITLE = "com.example.janda.photorun.models.title";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_run);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photorun");
        //joinDatabase = FirebaseDatabase.getInstance().getReference().child("photorun_settings");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        title_Textview = (TextView) findViewById(R.id.run_title); //not sure if link directs to the right button?
        //date_Textview = (TextView) findViewById(R.id.dateTextView);
        startpoint_Textview = (TextView) findViewById(R.id.start_pointTextView);
        endpoint_Textview = (TextView) findViewById(R.id.end_pointTextView);
        //starttime_Textview = (TextView) findViewById(R.id.starting_timeTextView);
        duration_Textview = (TextView) findViewById(R.id.estimated_durationTextView);
        description_Textview = (TextView) findViewById(R.id.descriptionTV);
        Textview_date = (TextView) findViewById(R.id.textViewDate);

        joinRunButton = (FloatingActionButton) findViewById(R.id.JoinButton);
        joinRunButton.setOnClickListener(this);

        attendButton = (FloatingActionButton) findViewById(R.id.attendButton);
        attendButton.setOnClickListener(this);

        test =(Button) findViewById(R.id.button2);
        test.setOnClickListener(this);

        //viewAtt = (AppCompatButton) findViewById(R.id.attendee_list);
        //viewAtt.setOnClickListener(this);


        Intent intent = getIntent();

        photorun_id = intent.getStringExtra(ViewPhotorunList.PHOTORUN_ID);

        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun").child(photorun_id).child("participants");
        listViewUsers = (ListView) findViewById(R.id.PhotoRunList);

        //list to store Photoruns
        users = new ArrayList<>();


        //Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Photowalk Details");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Hier können Sie alle Details des Photowalks einsehen, unter anderem können Sie auch die genaue Location auf der Karte einsehen. Ebenso sind alle angemeldeten Personen aufgelistet. Um am Photowalk teilzunehmen, wählen Sie bitte unten rechts \"Join Run\". Wenn Sie am Photowalk teilgenommen haben, können Sie unten rechts einen Haken setzen.");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewSinglePhotoRun.this, ViewPhotorunList.class);

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
                Intent myIntent = new Intent(ViewSinglePhotoRun.this, Users.class);

                finish();

                startActivity(myIntent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewSinglePhotoRun.this, ViewPhotorunList.class);

                finish();

                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewSinglePhotoRun.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);
            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



        //need to get photorun-ID from another activity!!!
/*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot photorunSnapshot: dataSnapshot.getChildren()){

                    //retrieve values of fields according to model class from firebase
                    Photorun singleRun = photorunSnapshot.getValue(Photorun.class);
                    date = singleRun.getDate().toString();
                    description = singleRun.getDescription().toString();
                    end_point = singleRun.getEnd_point().toString();
                    estimated_duration = singleRun.getEstimated_duration().toString();
                    max_participators = singleRun.getMax_participators().toString();
                    start_point = singleRun.getStart_point().toString();
                    starting_time = singleRun.getStarting_time().toString();
                    title = singleRun.getTitle().toString();

                    //add values to text view fields
                    date_Textview.setText(date);
                    description_Textview.setText(description);
                    endpoint_Textview.setText(end_point);
                    duration_Textview.setText(estimated_duration);
                    participants_Textview.setText(max_participators);
                    startpoint_Textview.setText(start_point);
                    starttime_Textview.setText(starting_time);
                    title_Textview.setText(title);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */

        //displayPhotoRun(photorun_id);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users.clear();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String user = userSnapshot.getKey();


                    users.add(user);
                    //findViewById(R.id.progressBar).setVisibility(View.GONE);

                }

                ViewAttendeesListAdapter adapter = new ViewAttendeesListAdapter(ViewSinglePhotoRun.this, users);

                listViewUsers.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        displayPhotoRun(photorun_id);
    }

    public void displayPhotoRun(String photorun_id) {
        DatabaseReference titleValue = mDatabase.child(photorun_id).child("title");

        titleValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title = dataSnapshot.getValue(String.class);
                title_Textview.setText(title);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    DatabaseReference dateValue = mDatabase.child(photorun_id).child("date");
        dateValue.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange (DataSnapshot dataSnapshot){
            date = dataSnapshot.getValue(String.class);
            Textview_date.setText(date);
        }

        @Override
        public void onCancelled (DatabaseError databaseError){

        }
    });


    DatabaseReference startpointValue = mDatabase.child(photorun_id).child("start_point");
        startpointValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                start_point = dataSnapshot.getValue(String.class);
                startpoint_Textview.setText(start_point);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        DatabaseReference endpointValue = mDatabase.child(photorun_id).child("end_point");
        endpointValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                end_point = dataSnapshot.getValue(String.class);
                endpoint_Textview.setText(end_point);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        DatabaseReference starttimeValue = mDatabase.child(photorun_id).child("starting_time");
        starttimeValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                starting_time = dataSnapshot.getValue(String.class);
                //starttime_Textview.setText(starting_time);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        DatabaseReference durationValue = mDatabase.child(photorun_id).child("estimated_duration");
        durationValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                estimated_duration = dataSnapshot.getValue(String.class);
                duration_Textview.setText("Am "+ date + " um " + starting_time + " ca. " + estimated_duration + " Stunden.");
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        DatabaseReference descriptionValue = mDatabase.child(photorun_id).child("description");
        descriptionValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                description = dataSnapshot.getValue(String.class);
                description_Textview.setText(description);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        final ImageButton mapsButton = (ImageButton) findViewById(R.id.showLocation);

        mapsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //closing activity
                finish();

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                //putting artist name and id to intent
                intent.putExtra(PHOTORUN_STARTPOINT, start_point);
                intent.putExtra(PHOTORUN_TITLE, title);
                intent.putExtra(PHOTORUN_ENDPOINT, end_point);
                //starting the activity with intent
                startActivity(intent);
            }
        });



    }

  /* public void checkPhotorun(){

       /*
       maxParticipators: Maximale Anzahl der Teilnehmer (Long)
        - maxParticipatorsRef: Datenbank-Referenz
        - maxParticipatorsString: Wertz aus Firebase als String (aktuell)

       curParticipators: Aktuelle Anzahl der Teilnehmer (Long)
        - ...
        */

        //get all the values needed to check it there are free spaces left
     /*  mDatabase.child("max_participators").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String maxPartBuffer = dataSnapshot.getValue(String.class);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       mDatabase.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               curPart = dataSnapshot.child("participants").getChildrenCount();
               maxPartBuffer = dataSnapshot.child("max_participators").getValue(String.class);
               joinPhotorun();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {
               curPart = dataSnapshot.child("participants").getChildrenCount();
               maxPartBuffer = dataSnapshot.child("max_participators").getValue(String.class);
               joinPhotorun();
           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               maxPart = 5;
               curPart = 0;
               joinPhotorun();
           }
       });} */


       public void joinPhotorun(){

           FirebaseUser currentuser = mAuth.getInstance().getCurrentUser();
           //String userID = currentuser.getUid();
           String participatorID = mAuth.getInstance().getCurrentUser().getUid();

         /*  maxPart = Long.parseLong(maxPartBuffer);

           if (maxPart == curPart) {

           Toast.makeText(this, "Sorry, this photorun is booked out. Please go back and choose another one.", Toast.LENGTH_LONG).show();

           } else { */

           mDatabase.child(photorun_id).child("participants").child(participatorID).setValue("enrolled");
           userDatabase.child(participatorID).child("participatedRuns").child(photorun_id).setValue("enrolled");

           Toast.makeText(this, "Successfully joined...", Toast.LENGTH_LONG).show();
       }

       public void attendPhotorun(){

           String ID = mAuth.getInstance().getCurrentUser().getUid();

           mDatabase.child(photorun_id).child("attendees").child(ID).setValue("enrolled");

           Toast.makeText(this, "Thanks for being here :)", Toast.LENGTH_LONG).show();



       }



    public void onClick(View view){
        if (view == joinRunButton){
            joinPhotorun();
        }
        if(view == viewAtt){
            finish();

            //creating an intent
            Intent intent = new Intent(getApplicationContext(), ViewAttendeesList.class);

            //putting artist name and id to intent
            intent.putExtra(ViewPhotorunList.PHOTORUN_ID, photorun_id);
            intent.putExtra(ViewPhotorunList.PHOTORUN_TITLE, title);
            //starting the activity with intent
            startActivity(intent);

        }

        if(view == attendButton) {
            attendPhotorun();
        }
        if(view == test){
            finish();

            Intent intent = new Intent(getApplicationContext(), ViewTeilnehmerliste.class);

            //putting artist name and id to intent
            intent.putExtra(ViewPhotorunList.PHOTORUN_ID, photorun_id);
            intent.putExtra(ViewPhotorunList.PHOTORUN_TITLE, title);
            //starting the activity with intent
            startActivity(intent);


        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ViewPhotorunList.class));
    }




}
