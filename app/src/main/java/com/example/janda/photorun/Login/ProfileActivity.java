package com.example.janda.photorun.Login;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.janda.photorun.MainActivity;
import com.example.janda.photorun.Photorun.CreateProfile;
import com.example.janda.photorun.Photorun.CreateRun;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.example.janda.photorun.R;


public class ProfileActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
   // private Button buttonLogout;
    // private Button buttonPhotorun;
    private Menu bottomMenu;

    private TextView toolbar_Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the User is not logged in
        //that means current User will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current User
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //buttonLogout = (Button) findViewById(R.id.buttonLogout);
       // buttonPhotorun = (Button) findViewById(R.id.createRunbutton);




        //displaying logged in User name
        //textViewUserEmail.setText("Logged in as: "+user.getEmail());

        //adding listener to button
        //buttonLogout.setOnClickListener(this);
        //buttonPhotorun.setOnClickListener(this);

        //Floating button settings
        final FloatingActionButton settingsBtn = (FloatingActionButton) findViewById(R.id.goToSettings);

        settingsBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, CreateProfile.class);

                finish();

                startActivity(myIntent);
            }
        });

        //Create Button
        final ImageButton profileBtn = (ImageButton) findViewById(R.id.Profilbtn);
        final ImageButton runBtn = (ImageButton) findViewById(R.id.Photorunbtn);
        final ImageButton searchBtn = (ImageButton) findViewById(R.id.Suchbtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, ViewPhotorunList.class);

                finish();

                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, CreateProfile.class);

                finish();

                startActivity(myIntent);
            }
        });

        //Logout Button von der Top toolbar, muss in jeder Activity da sein.

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //logging out the User
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                Intent myIntent = new Intent(ProfileActivity.this, LoginActivity.class);
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


        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Startseite");



    }

    /*@Override
    public void onClick(View view) {

        //if logout is pressed
        if(view == buttonLogout){
            //logging out the User
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(view == buttonPhotorun){
            finish();
            //go back to Create Photorun
            startActivity(new Intent(this, CreateRun.class));
        }

        }*/


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        bottomMenu = (Menu) findViewById(R.id.bottom_navigation);
        return true;
    }*/



}
