package com.example.janda.photorun.Photorun;

/**
 * Created by Tim Seemann on 04.10.2017.
 */



        import android.content.Intent;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.example.janda.photorun.Login.ProfileActivity;
        import com.example.janda.photorun.MainActivity;
        import com.example.janda.photorun.Photorun.ViewPhotoRuns;
        import com.example.janda.photorun.models.Photorun;
        import com.example.janda.photorun.Photorun.CreateRun;

        import com.example.janda.photorun.R;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;



public class ViewPhotorunList extends AppCompatActivity implements View.OnClickListener{

    public static final String PHOTORUN_TITLE = "com.example.janda.photorun.models.title";
    public static final String PHOTORUN_ID = "com.example.janda.photorun.models.photorun_id";

    private TextView toolbar_Textview;


    DatabaseReference databasePhotorun;

    ListView listViewPhotorun;
    List<Photorun> photoruns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_runs);


        //Liste von Photoruns anzeigen
        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun");
        listViewPhotorun = (ListView) findViewById(R.id.PhotoRunList);

        //list to store Photoruns
        photoruns = new ArrayList<>();

        //Create Button
        final FloatingActionButton createButton = (FloatingActionButton) findViewById(R.id.goToCreateRun);

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(ViewPhotorunList.this, CreateRun.class);
                startActivity(myIntent);
            }
        });

        listViewPhotorun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Photorun photorun = photoruns.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ViewSinglePhotoRun.class);

                //putting artist name and id to intent
                intent.putExtra(PHOTORUN_ID, photorun.getPhotorun_id());
                intent.putExtra(PHOTORUN_TITLE, photorun.getTitle());
                //starting the activity with intent
                startActivity(intent);

            }
        });

        //TOP TOOLBAR
        toolbar_Textview = (TextView) findViewById(R.id.layout_top_bar);
        toolbar_Textview.setText("Alle Photowalks");
    }


    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoruns.clear();

                for(DataSnapshot photorunSnapshot: dataSnapshot.getChildren()){

                    Photorun photorun = photorunSnapshot.getValue(Photorun.class);

                    photoruns.add(photorun);
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                }

                ViewPhotoRuns adapter = new ViewPhotoRuns(ViewPhotorunList.this, photoruns);

                listViewPhotorun.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {

        }
    public void onBackPressed() {
        finish();
        //go back to Create Photorun
        startActivity(new Intent(this, ProfileActivity.class));
    }

}



