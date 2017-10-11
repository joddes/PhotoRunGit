package com.example.janda.photorun.Photorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.Users;
import com.example.janda.photorun.Helpers.DatePickerFragment;
import com.example.janda.photorun.Helpers.TimePickerFragment;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRun extends AppCompatActivity implements View.OnClickListener {

    //public static final String Firebase_Server_URL = "https://photorun-3f474.firebaseio.com/";

    private FloatingActionButton submitButton;
    private Button backButton;


    EditText TitleEditText, DateEditText, Start_timeEditText, Estimated_durationEditText, Start_pointEditText, End_pointEditText ,Max_participatorsEditText, DescriptionEditText;
    private TextView toolbar_Textview;

    private DatabaseReference mDatabaseRefrence;

    private DatabaseReference photorunsEndPoint;
    private DatabaseReference photorun_settingsEndPoint;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_run);

        mDatabaseRefrence = FirebaseDatabase.getInstance().getReference();

        photorunsEndPoint = mDatabaseRefrence.child("Photorun");
        photorun_settingsEndPoint = mDatabaseRefrence.child("photorun_settings");

        submitButton = (FloatingActionButton) findViewById(R.id.submit_run);
        submitButton.setOnClickListener(this);


        TitleEditText = (EditText) findViewById(R.id.run_title);
        DateEditText = (EditText) findViewById(R.id.datum);
        DateEditText.setOnClickListener(this);

        Start_pointEditText = (EditText) findViewById(R.id.start_point);
        End_pointEditText = (EditText) findViewById(R.id.end_point);
        Start_timeEditText= (EditText) findViewById(R.id.starting_time);
        Start_timeEditText.setOnClickListener(this);

        Estimated_durationEditText = (EditText) findViewById(R.id.estimated_duration);
        DescriptionEditText = (EditText) findViewById(R.id.description);

//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Photowalk erstellen");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Um einen Photowalk zu erstellen, wählen Sie bitte einen aussagekräftigen Titel und fügen Sie eine Beschreibung hinzu (optional).\n" +
                "Ebenso bitte ein Datum mit Uhrzeit festlegen, sowie einen genauen Ort.\n" +
                "Falls Sie Probleme bei der Erstellung haben, bitten wir Sie support.brendamueller@photowalk.com zu kontaktieren.\n");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);
        //back
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateRun.this, ViewPhotorunList.class);

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
                Intent myIntent = new Intent(CreateRun.this, Users.class);
                finish();
                startActivity(myIntent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateRun.this, ViewPhotorunList.class);
                finish();
                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateRun.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);

            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }

    public void createRun(){
        String title = TitleEditText.getText().toString().trim();
        String date = DateEditText.getText().toString().trim();
        String start_point = Start_pointEditText.getText().toString().trim();
        String end_point = End_pointEditText.getText().toString().trim();
        String starting_time = Start_timeEditText.getText().toString().trim();
        String estimated_duration = Estimated_durationEditText.getText().toString().trim();
        //long estimated_duration = Long.parseLong(estimated_durationString);
        String description = DescriptionEditText.getText().toString().trim();

        if(TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }
        if(TextUtils.isEmpty(estimated_duration)) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }


        if(TextUtils.isEmpty(start_point)) {
            Toast.makeText(this, "Please enter start point", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(end_point)) {
            Toast.makeText(this, "Please enter end point", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(starting_time)) {
            Toast.makeText(this, "Please enter starting time", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        // get an ID as Primary Key
        String photorun_id = mDatabaseRefrence.push().getKey();

        String status = "future";

        //get the owner
        String owner = mAuth.getInstance().getCurrentUser().getUid();

        //create phtoruns object
        Photorun newPhotorun = new Photorun(date, description, estimated_duration, photorun_id, starting_time, title, start_point, end_point, status, owner);

        mDatabaseRefrence.child("Photorun").child(photorun_id).setValue(newPhotorun);
        mDatabaseRefrence.child("User").child(owner).child("createdRuns").child(photorun_id).setValue("created");

        Toast.makeText(this, "Photorun created...", Toast.LENGTH_LONG).show();

        TitleEditText.setText("");
        DateEditText.setText("");
        Start_pointEditText.setText("");
        End_pointEditText.setText("");
        Start_timeEditText.setText("");
        Estimated_durationEditText.setText("");
        DescriptionEditText.setText("");

    }

    @Override
    public void onClick(View view) {

        if(view == submitButton){
            createRun();
        }
        if (view == Start_timeEditText){
            showTimePickerDialog(Start_timeEditText);
        }
        if (view == DateEditText){
            showDatePickerDialog(DateEditText);
        }

    }
    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ViewPhotorunList.class));
    }

    public void showTimePickerDialog(View v){
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v){
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
