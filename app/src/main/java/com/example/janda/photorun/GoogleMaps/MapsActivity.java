package com.example.janda.photorun.GoogleMaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.Users;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.CreateRun;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker locationMarker;

    private String startpoint, title, endpoint;

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
        help_Textview.setText("Maps blblablub");

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
                Intent myIntent = new Intent(MapsActivity.this, Users.class);
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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Marker currentLocation;

        Intent intent = getIntent();
        startpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_STARTPOINT);
        title = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_TITLE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        String location = startpoint;

        Toast.makeText(this, location, Toast.LENGTH_LONG).show();

        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Startpoint: " +title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));

        }
    }
    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location , 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));

        }
    }



    public void changeType(View view) {
        /*if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            */
        Intent intent = getIntent();
        endpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_ENDPOINT);
        String location = endpoint;

        Toast.makeText(this, location, Toast.LENGTH_LONG).show();

        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            //locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Endpoint: " +title));
            MarkerOptions locationMarker1 = new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(latLng).title("Endpoint: " +title);
            mMap.addMarker(locationMarker1);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker1.getPosition(), 15));
        }


    }
}