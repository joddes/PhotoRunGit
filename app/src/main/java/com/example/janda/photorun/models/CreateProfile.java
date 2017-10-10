package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton submitButton;

    private EditText name, phonenumber, personal_information, address, role;

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

        phonenumber = (EditText) findViewById(R.id.phonenumber);

        personal_information = (EditText) findViewById(R.id.personal_information);

        address = (EditText) findViewById(R.id.address);

        email = (TextView) findViewById(R.id.mail);

        email.setText(user.getEmail());

        role = (EditText) findViewById(R.id.role);













    }

    public void createRun(){
        databaseProfiles =  FirebaseDatabase.getInstance().getReference();
        aktuelleUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String addr = address.getText().toString().trim();
        String full_name = name.getText().toString().trim();
        String email =  user.getEmail();
        String user_id = aktuelleUserID;
        String phone = phonenumber.getText().toString().trim();
        String personalinf = personal_information.getText().toString().trim();
        String rolle = role.getText().toString().trim();


        if(TextUtils.isEmpty(addr)) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(full_name)) {
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter phonenumber", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(personalinf)) {
            Toast.makeText(this, "Please enter personal information", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(rolle)) {
            Toast.makeText(this, "Please enter role", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }


        //create phtoruns object
        User newUserTest = new User(user_id, email, full_name, addr, phone, rolle, personalinf);



        databaseProfiles.child("User").child(aktuelleUserID).setValue(newUserTest);


        Toast.makeText(this, "User created...", Toast.LENGTH_LONG).show();



    }


    @Override
    public void onClick(View view) {

        if(view == submitButton){
            createRun();
            finish();
            //go back to Create Photorun
            startActivity(new Intent(this, ProfileActivity.class));
        }

    }
    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ProfileActivity.class));
    }


}
