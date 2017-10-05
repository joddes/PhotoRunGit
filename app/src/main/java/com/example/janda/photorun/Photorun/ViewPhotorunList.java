package com.example.janda.photorun.Photorun;

/**
 * Created by Tim Seemann on 04.10.2017.
 */



        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;

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

        }


    @Override
    protected void onStart() {
        super.onStart();

        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photoruns.clear();

                for(DataSnapshot photorunSnapshot: dataSnapshot.getChildren()){

                    Photorun photorun = photorunSnapshot.getValue(Photorun.class);

                    photoruns.add(photorun);
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

    }



