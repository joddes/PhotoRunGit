package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.ViewUserList;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
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

        //Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        TextView toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Profil Bearbeiten");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Hier haben Sie die Möglichkeit, die anderen User auf sich aufmerksam zu machen!\n" +
                "Sie können ein Profilbild hochladen und Ihre Kontaktdaten wie Email, Telefonnummer, Name, Beruf und Adresse eingeben. \n" +
                "Ebenso können Sie Hobbies und Interessen eintragen, mit denen Sie Ihrem Profil eine persönliche Note geben können.\n" +
                "Um Ihre Änderungen abzuspeichern, klicken Sie bitte den Button unten rechts.\n");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);
        //back
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ProfileActivity.class);

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
                Intent myIntent = new Intent(CreateProfile.this, ViewUserList.class);
                finish();
                startActivity(myIntent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ViewPhotorunList.class);
                finish();
                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);

            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>













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
