package com.example.janda.photorun.Photorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ViewSinglePhotoRun extends AppCompatActivity implements View.OnClickListener  {

    //implements View.OnClickListener

    private TextView title_Textview, date_Textview, startpoint_Textview, endpoint_Textview, starttime_Textview, duration_Textview, participants_Textview, description_Textview;

    private Button joinRunButton;

    private String date, description, end_point, estimated_duration, max_participators, start_point, starting_time, title;

    private DatabaseReference mDatabase, joinDatabase;

    private FirebaseAuth mAuth;


    DatabaseReference Test;

    String photorun_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_run);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Photorun");
        joinDatabase = FirebaseDatabase.getInstance().getReference().child("photorun_settings");

        title_Textview = (TextView) findViewById(R.id.run_title); //not sure if link directs to the right button?
        date_Textview = (TextView) findViewById(R.id.dateTextView);
        startpoint_Textview = (TextView) findViewById(R.id.start_pointTextView);
        endpoint_Textview = (TextView) findViewById(R.id.end_pointTextView);
        starttime_Textview = (TextView) findViewById(R.id.starting_timeTextView);
        duration_Textview = (TextView) findViewById(R.id.estimated_durationTextView);
        participants_Textview = (TextView) findViewById(R.id.max_participatorsTextView);
        description_Textview = (TextView) findViewById(R.id.descriptionTV);

        joinRunButton = (Button) findViewById(R.id.JoinButton);
        joinRunButton.setOnClickListener(this);


        Intent intent = getIntent();

        photorun_id = intent.getStringExtra(ViewPhotorunList.PHOTORUN_ID);

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
            date_Textview.setText(date);
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
                starttime_Textview.setText(starting_time);
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
                duration_Textview.setText(estimated_duration);
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

        DatabaseReference maxparticipatorsValue = mDatabase.child(photorun_id).child("max_participators");
        maxparticipatorsValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                max_participators = dataSnapshot.getValue(String.class);
                participants_Textview.setText(max_participators);
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



    }

   public void joinPhotorun(){

       FirebaseUser currentuser = mAuth.getInstance().getCurrentUser();
       //String userID = currentuser.getUid();
       String participatorID = mAuth.getInstance().getCurrentUser().getUid();
       

      /* //check if there is still space for more participants
       DatabaseReference maxparticipatorsValue = mDatabase.child(photorun_id).child("max_participators");
       maxparticipatorsValue.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange (DataSnapshot dataSnapshot){
               max_participators = dataSnapshot.getValue(String.class);
               int partNumber = Integer.parseInt(max_participators);
           }

           @Override
           public void onCancelled (DatabaseError databaseError){

           }
       });

       DatabaseReference currentParticipators = joinDatabase.child("participants").child(participatorID);
       int participatorsCount;
       currentParticipators.addChildEventListener(new ChildEventListener() {
           int participatorsCount
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               participatorsCount = (int) dataSnapshot.getChildrenCount();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {
               participatorsCount = (int) dataSnapshot.getChildrenCount();
           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

      // if (participatorsCount == maxparticipatorsValue)

      */



       //check if user already joined: join should only be possible once


       joinDatabase.child(photorun_id).child("participants").child(participatorID).setValue("enrolled");
       joinDatabase.child(photorun_id).child("participators").setValue("true");

       Toast.makeText(this, "Successfully joined...", Toast.LENGTH_LONG).show();

}

    public void onClick(View view){
        if (view == joinRunButton){
            joinPhotorun();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ProfileActivity.class));
    }

}
