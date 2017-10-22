package com.example.janda.photorun.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.janda.photorun.Chat.ViewUserList;
import com.example.janda.photorun.GoogleMaps.MapsActivity;
import com.example.janda.photorun.Photorun.PersonalListadapter;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.models.CreateProfile;
import com.example.janda.photorun.models.Photorun;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.janda.photorun.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.janda.photorun.Photorun.ViewPhotorunList.PHOTORUN_ID;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail, textViewphone, textViewname, textViewhobbies, textViewaddress, textViewrole;
   // private Button buttonLogout;
    // private Button buttonPhotorun;
    private Menu bottomMenu;

    private TextView toolbar_Textview;

    String userID, mail, phonenumber, name, hobbies, address, role;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public String clickedPhotorun;

    private DatabaseReference mRef;

    public static String ubergabeName;

    private FloatingActionButton settingsBtn;

    DatabaseReference databasePhotorunUser;
    FirebaseAuth mAuth;

    ListView listViewPhotoruns;
    List<String> photoruns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userID = user.getUid();



        mRef = FirebaseDatabase.getInstance().getReference().child("User");

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


        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.mail);
        textViewaddress = (TextView) findViewById(R.id.address);
        textViewhobbies = (TextView) findViewById(R.id.personal_information);
        textViewname = (TextView) findViewById(R.id.name);
        textViewphone = (TextView) findViewById(R.id.phonenumber);
        textViewrole = (TextView) findViewById(R.id.role);

        //buttonLogout = (Button) findViewById(R.id.buttonLogout);
       // buttonPhotorun = (Button) findViewById(R.id.createRunbutton);

        databasePhotorunUser = FirebaseDatabase.getInstance().getReference("User").child(userID).child("participatedRuns");
        listViewPhotoruns = (ListView) findViewById(R.id.enrolledListView);

        //list to store Photoruns
        photoruns = new ArrayList<>();


        listViewPhotoruns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                clickedPhotorun = photoruns.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ViewSinglePhotoRun.class);

                //putting artist name and id to intent
                intent.putExtra(PHOTORUN_ID, clickedPhotorun);
                //starting the activity with intent
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ProfileActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(intent, options.toBundle());

            }
        });

        //displaying logged in User name
        //textViewUserEmail.setText("Logged in as: "+user.getEmail());

        //adding listener to button
        //buttonLogout.setOnClickListener(this);
        //buttonPhotorun.setOnClickListener(this);

        //Floating button settings
        settingsBtn = (FloatingActionButton) findViewById(R.id.goToSettings);
        settingsBtn.setOnClickListener(this);

        settingsBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, CreateProfile.class);

                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ProfileActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }


        });

        //Create Button
        final ImageButton profileBtn = (ImageButton) findViewById(R.id.Profilbtn);
        final ImageButton runBtn = (ImageButton) findViewById(R.id.Photorunbtn);
        final ImageButton searchBtn = (ImageButton) findViewById(R.id.Suchbtn);
        final ImageButton mapBtn = (ImageButton) findViewById(R.id.Mapbtn);
        findViewById(R.id.menu4).setBackgroundResource(R.color.white);
        profileBtn.setBackgroundResource(R.drawable.go_profile_icon_orange);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, ViewUserList.class);
                findViewById(R.id.menu1).setBackgroundResource(R.color.white);
                searchBtn.setBackgroundResource(R.drawable.messenger_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ProfileActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, ViewPhotorunList.class);
                findViewById(R.id.menu3).setBackgroundResource(R.color.white);
                runBtn.setBackgroundResource(R.drawable.go_run_icon_orange);
                findViewById(R.id.menu4).setBackgroundResource(R.color.colorAccent);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ProfileActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, CreateProfile.class);

                finish();

                startActivity(myIntent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ProfileActivity.this, MapsActivity.class);
                ViewSinglePhotoRun.mapInd=0;
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
        findViewById(R.id.help_seite).bringToFront();
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
        //toolbar_Textview.setText("Dein Profil");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Das ist Ihr Profil!\n" +
                "Falls Sie es bearbeiten möchten, klicken Sie bitte auf den Button unten rechts.\n" +
                "WICHTIG: Sie müssen zuerst ein Profil anlegen, bevor sie den Chat benutzen können.");

        //displayPhotoRun(userID);



    }

    @Override
    protected void onStart() {
        super.onStart();
        //findViewById(R.id.progressBar).setVisibility(View.VISIBLE);


        databasePhotorunUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoruns.clear();

                for (DataSnapshot photorunSnapshot : dataSnapshot.getChildren()) {
                    String photorun = photorunSnapshot.getKey();
                    photoruns.add(photorun);
                }
                //findViewById(R.id.progressBar).setVisibility(View.GONE);
                PersonalListadapter adapter = new PersonalListadapter(ProfileActivity.this, photoruns);

                listViewPhotoruns.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        displayPhotoRun(userID);
    }

    public void displayPhotoRun(String userID) {
        DatabaseReference email = mRef.child(userID).child("email");

        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mail = dataSnapshot.getValue(String.class);
                textViewUserEmail.setText(mail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference addr = mRef.child(userID).child("address");

        addr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               address  = dataSnapshot.getValue(String.class);
                textViewaddress.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference phonenum = mRef.child(userID).child("phonenumber");

        phonenum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phonenumber = dataSnapshot.getValue(String.class);
                textViewphone.setText(phonenumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference fullname = mRef.child(userID).child("full_name");

        fullname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                toolbar_Textview.setText(name);
                ubergabeName = name;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference rol = mRef.child(userID).child("role");

        rol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                role = dataSnapshot.getValue(String.class);
                textViewrole.setText(role);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference hob = mRef.child(userID).child("personalInf");

        hob.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hobbies = dataSnapshot.getValue(String.class);
                textViewhobbies.setText(hobbies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        //if logout is pressed
        if(view == settingsBtn){
            //logging out the User
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, CreateProfile.class));
        }


        }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        bottomMenu = (Menu) findViewById(R.id.bottom_navigation);
        return true;
    }*/



}
