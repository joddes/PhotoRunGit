package com.example.janda.photorun.models;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.ViewUserList;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.CreateRun;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class CreateProfile extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton submitButton;

    private EditText name, phonenumber, personal_information, address, role;

    private DatabaseReference databaseProfiles;

    private TextView email;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Map following, followers;
    String nameMark;
    String ubername= ProfileActivity.ubergabeName;


    private String aktuelleUserID;
    private DatabaseReference mRef;
    DatabaseReference databaseFullName;


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
        final ImageButton mapBtn = (ImageButton) findViewById(R.id.Mapbtn);
        findViewById(R.id.menu4).setBackgroundResource(R.color.white);
        profileBtn.setBackgroundResource(R.drawable.go_profile_icon_orange);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ViewUserList.class);
                findViewById(R.id.menu1).setBackgroundResource(R.color.white);
                searchBtn.setBackgroundResource(R.drawable.messenger_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(CreateProfile.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ViewPhotorunList.class);
                findViewById(R.id.menu3).setBackgroundResource(R.color.white);
                runBtn.setBackgroundResource(R.drawable.go_run_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(CreateProfile.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(CreateProfile.this, ProfileActivity.class);

                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(CreateProfile.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());

            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>













    }

    public void createProfile(){
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
            addr = "";
        }

        if(TextUtils.isEmpty(full_name)) {
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(phone)) {
            phone = "";
        }

        if(TextUtils.isEmpty(personalinf)) {
            personalinf = "";
        }

        if(TextUtils.isEmpty(rolle)) {
            rolle = "";
        }


        //create phtoruns object
        User newUserTest = new User(user_id, email, full_name, addr, phone, rolle, personalinf);


        databaseProfiles.child("User").child(aktuelleUserID).setValue(newUserTest);


        Toast.makeText(this, "Profil erstellt..", Toast.LENGTH_LONG).show();

    }
    public void updateProfile(){
        databaseProfiles =  FirebaseDatabase.getInstance().getReference();
        aktuelleUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String addr = address.getText().toString().trim();
        String full_name = name.getText().toString().trim();
        String email =  user.getEmail();
        String user_id = aktuelleUserID;
        String phone = phonenumber.getText().toString().trim();
        String personalinf = personal_information.getText().toString().trim();
        String rolle = role.getText().toString().trim();


        if(!TextUtils.isEmpty(addr)) {
            databaseProfiles.child("User").child(aktuelleUserID).child("address").setValue(addr);
        }

        if(!TextUtils.isEmpty(full_name)) {
            databaseProfiles.child("User").child(aktuelleUserID).child("full_name").setValue(full_name);
        }

        if(!TextUtils.isEmpty(phone)) {
            databaseProfiles.child("User").child(aktuelleUserID).child("phonenumber").setValue(phone);
        }

        if(!TextUtils.isEmpty(personalinf)) {
            databaseProfiles.child("User").child(aktuelleUserID).child("personalInf").setValue(personalinf);
        }

        if(!TextUtils.isEmpty(rolle)) {
            databaseProfiles.child("User").child(aktuelleUserID).child("role").setValue(rolle);
        }

        Toast.makeText(this, "Profil aktualisiert..", Toast.LENGTH_LONG).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {

        if(view == submitButton){

            if(ubername==null) {
                createProfile();
            }else{
                updateProfile();
            }
            //go back to Create Photorun

            RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(CreateProfile.this,
                            lala,
                            ViewCompat.getTransitionName(lala));
            startActivity(new Intent(this, ProfileActivity.class),options.toBundle());
        }

    }
    @Override
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ProfileActivity.class));
    }


}
