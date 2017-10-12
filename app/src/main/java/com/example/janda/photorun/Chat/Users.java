package com.example.janda.photorun.Chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText, toolbar_Textview;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    private DatabaseReference mDatabase;

    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    static String username = "";
    static String password = "";
    static String gegen = "";
    static String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        mRef = FirebaseDatabase.getInstance().getReference().child("User");

        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String userid = mAuth.getInstance().getCurrentUser().getUid();



        String url = "https://photorun-3f474.firebaseio.com/User.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
                //changelist();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                findFullName(al.get(position));
                startActivity(new Intent(Users.this, Chat.class));
            }
        });


        //Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Schreibe einem User");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Lieber Benutzer,\n" +
                "leider steht auf dieser Seite noch keine Hilfe zur Verfügung.\n" +
                "Mehr Infos folgen in Kürze.");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(Users.this, ProfileActivity.class);

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

            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(Users.this, ViewPhotorunList.class);

                finish();

                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(Users.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);
            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }
    public void findFullName(String key){

        DatabaseReference fullname = mRef.child(key).child("full_name");
        fullname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changelist(){
        int j = 0;
        while (al.size() > j) {
            DatabaseReference fullname = mRef.child(al.get(j)).child("full_name");
            fullname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        gegen = dataSnapshot.getValue(String.class);
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            al.set(j, gegen);
            j++;
        }
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String key = "";
            username = mAuth.getInstance().getCurrentUser().getUid();

            while(i.hasNext()){
                key = i.next().toString();
                if(!key.equals(username)) {
                    al.add(key);
                }

                totalUsers++;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{

            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }


}