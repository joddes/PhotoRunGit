package com.example.janda.photorun.Photorun;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PersonalListadapter extends ArrayAdapter<String> {


    private Activity context;
    private List<String> photorunList;
    public static Photorun photorun;
    private DatabaseReference databasePhotorun;


    public PersonalListadapter(Activity context, List<String> photorunList) {
        super(context, R.layout.activity_personal_list_adapter, photorunList);
        // super(context, R.layout.layout_photorun_list, userList);
        this.context = context;
        this.photorunList = photorunList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_personal_list_adapter, null, true);

        final TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.run_title);
        final TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        String photorunID = photorunList.get(position);


        databasePhotorun = FirebaseDatabase.getInstance().getReference("Photorun").child(photorunID);




        databasePhotorun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photorun = dataSnapshot.getValue(Photorun.class);
                textViewTitle.setText(photorun.getTitle());
                textViewDate.setText(photorun.getDate());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listViewItem;


    }
}
