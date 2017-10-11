package com.example.janda.photorun.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.R;
import com.example.janda.photorun.models.User;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class ViewAttendeesListAdapter extends ArrayAdapter<String> {


    private Activity context;
    private List<String> userList;
    public static User aktuellerUser;
    private DatabaseReference databaseProfile;


    public ViewAttendeesListAdapter(Activity context, List<String> userList) {
        super(context, R.layout.layout_attendees_list, userList);
        // super(context, R.layout.layout_photorun_list, userList);
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_attendees_list, null, true);

        final TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.run_title);
        final TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        String userID = userList.get(position);


        databaseProfile = FirebaseDatabase.getInstance().getReference("User").child(userID);




        databaseProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                aktuellerUser = dataSnapshot.getValue(User.class);
               textViewTitle.setText(aktuellerUser.getFull_name());
               textViewDate.setText(aktuellerUser.getEmail());
                textViewDate.setText(aktuellerUser.getUser_id());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listViewItem;


    }
}
