package com.example.janda.photorun.Photorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.janda.photorun.R;
import com.example.janda.photorun.models.photoruns;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRun extends AppCompatActivity {

    //public static final String Firebase_Server_URL = "https://photorun-3f474.firebaseio.com/";

    private Button SubmitButton;

    private EditText TitleEditText, DateEditText, Start_timeEditText, Estimated_durationEditText, Start_pointEditText, End_pointEditText ,Max_participatorsEditText, DescriptionEditText;

    DatabaseReference mDatabaseRefrence;

    FirebaseDatabase mFirebaseDatabase;

    DatabaseReference photorunsEndPoint;
    DatabaseReference photorun_settingsEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRefrence = mFirebaseDatabase.getReference();

        photorunsEndPoint = mDatabaseRefrence.child("photoruns");
        photorun_settingsEndPoint = mDatabaseRefrence.child("photorun_settings");

        SubmitButton = (Button) findViewById(R.id.submit_run);

        TitleEditText = (EditText) findViewById(R.id.run_title);
        DateEditText = (EditText) findViewById(R.id.datum);
        Start_pointEditText = (EditText) findViewById(R.id.start_point);
        End_pointEditText = (EditText) findViewById(R.id.end_point);
        Start_timeEditText= (EditText) findViewById(R.id.starting_time);
        Estimated_durationEditText = (EditText) findViewById(R.id.estimated_duration);
        Max_participatorsEditText = (EditText) findViewById(R.id.max_participators);
        DescriptionEditText = (EditText) findViewById(R.id.description);

    }

    public void createRun(){
        String title = TitleEditText.getText().toString().trim();
        String date = DateEditText.getText().toString().trim();
        String start_point = Start_pointEditText.toString().trim();
        String end_point = End_pointEditText.toString().trim();
        String starting_time = Start_timeEditText.toString().trim();
        String estimated_durationString = Estimated_durationEditText.toString().trim();
        long estimated_duration = Long.parseLong(estimated_durationString);
        String max_participatorsString = Max_participatorsEditText.toString().trim();
        long max_participators = Long.parseLong(max_participatorsString);
        String description = DescriptionEditText.toString().trim();

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

        //create phtoruns object
        photoruns newPhotorun = new photoruns(date, description, estimated_duration, max_participators, photorun_id, starting_time, title, start_point, end_point);

        mDatabaseRefrence.child("photoruns").child(photorun_id).setValue(newPhotorun);

        TitleEditText.setText("");
        DateEditText.setText("");
        Start_pointEditText.setText("");
        End_pointEditText.setText("");
        Start_timeEditText.setText("");
        Estimated_durationEditText.setText("");
        Max_participatorsEditText.setText("");
        DescriptionEditText.setText("");

        Toast.makeText(this, "PhtoRun created", Toast.LENGTH_LONG).show();
    }

    public void submit(){
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRun();
            }
        });

    }}
