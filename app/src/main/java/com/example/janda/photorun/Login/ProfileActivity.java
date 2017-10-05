package com.example.janda.photorun.Login;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.janda.photorun.MainActivity;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //buttonLogout = (Button) findViewById(R.id.buttonLogout);
       // buttonPhotorun = (Button) findViewById(R.id.createRunbutton);




        //displaying logged in user name
        textViewUserEmail.setText("Logged in as: "+user.getEmail());

        //adding listener to button
        //buttonLogout.setOnClickListener(this);
        //buttonPhotorun.setOnClickListener(this);

    }

    /*@Override
    public void onClick(View view) {

        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
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



    public void clickExit(MenuItem item){

        //logging out the user
        firebaseAuth.signOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(this, LoginActivity.class));

    }

    public void clickSearch(MenuItem item){
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ViewPhotorunList.class));
    }

    public void clickProfile(MenuItem item){
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ViewSinglePhotoRun.class));
    }

    public void clickRun(MenuItem item){

        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, CreateRun.class));

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        bottomMenu = (Menu) findViewById(R.id.bottom_navigation);
        return true;
    }*/



}
