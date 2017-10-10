package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.R;
import com.example.janda.photorun.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton submitButton;

    private EditText username, name;

    private DatabaseReference databaseProfiles;

    private TextView email;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private String aktuelleUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //databaseProfiles = FirebaseDatabase.getInstance().getReference().child("User").child(aktuelleUserID);
        //Test: Toast.makeText(CreateProfile.this, databaseProfiles.getKey(), Toast.LENGTH_SHORT).show();


        submitButton = (FloatingActionButton) findViewById(R.id.submit_user);
        submitButton.setOnClickListener(this);


        name = (EditText) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.mail);

        email.setText(user.getEmail());

        username = (EditText) findViewById(R.id.phonenumber);
        name = (EditText) findViewById(R.id.name);











    }

    public void createRun(){
        databaseProfiles =  FirebaseDatabase.getInstance().getReference();
        aktuelleUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String user_name = username.getText().toString().trim();
        String full_name = name.getText().toString().trim();
        String email =  user.getEmail();
        String user_id = aktuelleUserID;


        if(TextUtils.isEmpty(user_name)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(full_name)) {
            Toast.makeText(this, "Please enter Full name", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }


        //create phtoruns object
        User newUserTest = new User(user_id, email, full_name, user_name);



        databaseProfiles.child("User").child(aktuelleUserID).setValue(newUserTest);


        Toast.makeText(this, "User created...", Toast.LENGTH_LONG).show();



    }


    @Override
    public void onClick(View view) {

        if(view == submitButton){
            finish();
            //go back to Create Photorun
            startActivity(new Intent(this, com.example.janda.photorun.Photorun.CreateProfile.class));
        }

    }
    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ProfileActivity.class));
    }


}
