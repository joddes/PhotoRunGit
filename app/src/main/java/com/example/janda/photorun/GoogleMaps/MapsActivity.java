package com.example.janda.photorun.GoogleMaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.Chat.ViewUserList;
import com.example.janda.photorun.Login.ProfileActivity;
import com.example.janda.photorun.Photorun.ViewPhotoRuns;
import com.example.janda.photorun.Photorun.ViewPhotorunList;
import com.example.janda.photorun.Photorun.ViewSinglePhotoRun;
import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.example.janda.photorun.models.User;
import com.example.janda.photorun.models.ViewProfile;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.janda.photorun.Photorun.PersonalListadapter.photorun;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {


    private static final int REQUEST_FINE_LOCATION = 0;
    private GoogleMap mMap;
    private Marker locationMarker;
    private String startpoint, endpoint, title;
    private Geocoder geocoder;
    private DatabaseReference databaseWalks;
    private String eventID;
    private FirebaseAuth mAuth;
    final String username = mAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(username);

    public static final String PHOTORUN_TITLE = "com.example.janda.photorun.models.title";
    public static final String PHOTORUN_ID = "com.example.janda.photorun.models.photorun_id";

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 12000;  /* 120 secs */
    private long FASTEST_INTERVAL = 30000; /* 30 sec */


    String photorun_id, photorun_title;
    String locationAll;
    List<Photorun> photoruns;


    GeoFire geoFire = new GeoFire(mDatabase);


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
                ViewSinglePhotoRun.mapInd = 0;
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
        final ImageButton mapBtn = (ImageButton) findViewById(R.id.Mapbtn);
        if (ViewSinglePhotoRun.mapInd == 1) {
            findViewById(R.id.menu3).setBackgroundResource(R.color.white);
            runBtn.setBackgroundResource(R.drawable.go_run_icon_orange);
        } else {
            findViewById(R.id.menu2).setBackgroundResource(R.color.white);
            mapBtn.setBackgroundResource(R.drawable.go_map_icon_orange);
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ViewUserList.class);
                findViewById(R.id.menu1).setBackgroundResource(R.color.white);
                searchBtn.setBackgroundResource(R.drawable.messenger_icon_orange);
                findViewById(R.id.menu3).setBackgroundResource(R.color.colorAccent);
                mapBtn.setBackgroundResource(R.drawable.go_map_icon);
                findViewById(R.id.menu2).setBackgroundResource(R.color.colorAccent);
                runBtn.setBackgroundResource(R.drawable.go_run_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MapsActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                ViewSinglePhotoRun.mapInd = 0;
                startActivity(myIntent, options.toBundle());
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                ViewSinglePhotoRun.mapInd = 0;
                Intent myIntent = new Intent(MapsActivity.this, MapsActivity.class);
                findViewById(R.id.menu2).setBackgroundResource(R.color.white);
                mapBtn.setBackgroundResource(R.drawable.go_map_icon_orange);
                findViewById(R.id.menu3).setBackgroundResource(R.color.colorAccent);
                runBtn.setBackgroundResource(R.drawable.go_run_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MapsActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                startActivity(myIntent, options.toBundle());
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ViewPhotorunList.class);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MapsActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                ViewSinglePhotoRun.mapInd = 0;
                startActivity(myIntent, options.toBundle());
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View view) {
                Intent myIntent = new Intent(MapsActivity.this, ProfileActivity.class);
                findViewById(R.id.menu4).setBackgroundResource(R.color.white);
                profileBtn.setBackgroundResource(R.drawable.go_profile_icon_orange);
                findViewById(R.id.menu2).setBackgroundResource(R.color.colorAccent);
                mapBtn.setBackgroundResource(R.drawable.go_map_icon);
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MapsActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                ViewSinglePhotoRun.mapInd = 0;
                startActivity(myIntent, options.toBundle());

            }
        });


//Die Navigationsleisten>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        DatabaseReference databasePhotorun;
        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun");
        //list to store Photoruns
        photoruns = new ArrayList<>();

        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoruns.clear();


                for (DataSnapshot photorunSnapshot : dataSnapshot.getChildren()) {

                    Photorun photorun = photorunSnapshot.getValue(Photorun.class);

                    photoruns.add(photorun);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseWalks = FirebaseDatabase.getInstance().getReference().child("Photorun");
        startLocationUpdates();

    }

    public void geoCode(String eventLocation) throws IOException {

        geocoder = new Geocoder(this);


        List<Address> addressList = geocoder.getFromLocationName(eventLocation, 1);
        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());


        if (ViewSinglePhotoRun.mapInd == 1) {
            locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Photorun: " + title).snippet(eventLocation));

        } else {
            locationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Photorun: " + photorun_title).snippet(eventLocation));


        }

        locationMarker.hideInfoWindow();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (checkPermissions()) {
            mMap.setMyLocationEnabled(true);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (ViewSinglePhotoRun.mapInd == 1) {
            Intent intent = getIntent();
            startpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_STARTPOINT);
            title = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_TITLE);
            endpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_ENDPOINT);


            //String location = startpoint;
            //Button Btype = (Button)findViewById(R.id.Btype);
            //Btype.setText("Ziel");
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);

            try {


                geoCode(endpoint);
                geoCode(startpoint);


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 13));

            } catch (IOException e) {
                e.printStackTrace();
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                public void onInfoWindowClick(Marker marker) {
                    finish();

                }
            });

        } else {

            showAllWalks();
        }

        mMap.setOnInfoWindowClickListener(this);

    }


    public void showDestination(View view) throws IOException {
        if (ViewSinglePhotoRun.mapInd == 1) {
            Intent intent = getIntent();
            endpoint = intent.getStringExtra(ViewSinglePhotoRun.PHOTORUN_ENDPOINT);
            String location = endpoint;

            geoCode(endpoint);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));
        } else {
            showAllWalks();
        }
    }


    public void onSearch(View view) {
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        if (location != null || !location.equals("")) {
            try {
                geoCode(location);
                locationMarker.hideInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 15));


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Bitte geben Sie ein Ziel in die Suchleiste ein!", Toast.LENGTH_LONG).show();
        }
    }


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FUNKTION FÜR DAS ANZEIGEN ALLER WALKS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public void showAllWalks() {

        for (int i = 0; i < photoruns.size(); i++) {
            Photorun photorun = photoruns.get(i);
            photorun_id = photorun.getStart_point();
            photorun_title = photorun.getTitle();

            try {
                geoCode(photorun_id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onInfoWindowClick(Marker marker) {
                RelativeLayout lala = (RelativeLayout) findViewById(R.id.bottom_navigation_bar);

                Intent intent = new Intent(getApplicationContext(), ViewSinglePhotoRun.class);
                //putting artist name and id to intent
                intent.putExtra(PHOTORUN_ID, photorun.getPhotorun_id());
                intent.putExtra(PHOTORUN_TITLE, photorun.getTitle());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MapsActivity.this,
                                lala,
                                ViewCompat.getTransitionName(lala));
                //starting the activity with intent
                startActivity(intent, options.toBundle());

            }
        });


    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>LOCATION TRACKER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        geoFire.setLocation("location", new GeoLocation(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();

    }
}



