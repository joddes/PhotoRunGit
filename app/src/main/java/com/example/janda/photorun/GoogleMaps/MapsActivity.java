package com.example.janda.photorun.GoogleMaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.R;
import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    private Marker locationMarker;
    private DatabaseReference mDatabase;
    private String startpoint, title, endpoint;
    private Geocoder geocoder;
    private DatabaseReference databaseWalks;
    private String eventID;
    String photorun_id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //TOP TOOLBAR------------------------------------------------------------------
        TextView toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Kartenansicht");
        TextView help_Textview = (TextView) findViewById(R.id.help_inhalt);
        help_Textview.setText("Auf dieser Karte können Sie sich den Startpunkt des Photowalks anschauen. \n"
                + "Klicken Sie oben rechts auf 'Ziel', um den Endpunkt des Photowalks angezeigt zu bekommen.\n"
                + "Sie können ebenfalls nach weiteren Orten suchen. \n"
                + "Geben Sie dafür in der Suchleiste den gewünschten Ort ein und klicken anschließend auf 'Suchen'\n"
                + "Um sich zu einem der Orte navigieren zu lassen, tippen Sie auf den jeweiligen Marker und dann anschließend unten rechts auf den Pfeil. \n"
                + "Daraufhin wird Google Maps gestartet, sofern die App auf Ihrem mobilen Endgerät installiert ist");

        final ImageButton logoutBtn = (ImageButton) findViewById(R.id.logout_icon);
        //back
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ViewPhotorunList.class);

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
                if (var == 1) {
                    findViewById(R.id.help_seite).startAnimation(slide_left);
                    findViewById(R.id.help_seite).setVisibility(View.VISIBLE);
                    findViewById(R.id.help_icon).setBackgroundResource(R.drawable.cancel_icon);
                    var = 0;
                } else {
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
                Intent myIntent = new Intent(MapsActivity.this, ViewUserList.class);
                finish();
                startActivity(myIntent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ViewPhotorunList.class);
                finish();
                startActivity(myIntent);
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ProfileActivity.class);

                finish();

                startActivity(myIntent);

            }
        });
//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

       databaseWalks = FirebaseDatabase.getInstance().getReference().child("Photorun");

    }

    public void geoCode(String eventLocation) throws IOException {

        geocoder = new Geocoder(this);


        List<Address> addressList = geocoder.getFromLocationName(eventLocation, 1);
        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Photorun: " + title).snippet("Startpunkt: " + eventLocation));
        locationMarker.showInfoWindow();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        Intent intent = getIntent();
        startpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_STARTPOINT);
        title = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_TITLE);

        String location = startpoint;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            geoCode(startpoint);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showDestination(View view) throws IOException {
        Intent intent = getIntent();
        endpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_ENDPOINT);
        String location = endpoint;

        geoCode(endpoint);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));

    }



    public void onSearch(View view) {
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        if (location != null || !location.equals("")) {
            try {
                geoCode(location);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Bitte geben Sie ein Ziel in die Suchleiste ein!", Toast.LENGTH_LONG).show();
        }
    }


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FUNKTION FÜR DAS ANZEIGEN ALLER WALKS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public void showAllWalks(View view) {

        Intent intent = getIntent();

        photorun_id = intent.getStringExtra(ViewPhotorunList.PHOTORUN_ID);

        mDatabase = FirebaseDatabase.getInstance().getReference("Photrun").child(photorun_id).child("start_point");
        mDatabase .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String location = userSnapshot.getKey();
                    try {
                        geoCode(location);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // String location = mDatabase.getKey();
        /*try {
            geoCode(location);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //Toast.makeText(this, location, Toast.LENGTH_LONG).show();

    }

    //>>>>>>>>>>>>>>>>>>>>>>>




    //method enables user to search for other locations on map



}