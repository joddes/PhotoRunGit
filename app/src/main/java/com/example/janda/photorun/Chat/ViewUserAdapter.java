package com.example.janda.photorun.Chat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.example.janda.photorun.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class ViewUserAdapter extends ArrayAdapter<User> {


    private Activity context;

    String neu;

    private List<User> userList;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    Firebase reference3;
    public ViewUserAdapter(Activity context, List<User> userList){
        super(context, R.layout.layout_user_list, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        //TextView textViewUserId = (TextView) listViewItem.findViewById(R.id.textViewUserId);
        TextView textViewFullName = (TextView) listViewItem.findViewById(R.id.textViewFullName);
        final ImageView newMessage = (ImageView) listViewItem.findViewById(R.id.newMessages);

        User user = userList.get(position);
        final String chatWith_id = user.getUser_id();
        final String username = mAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("messages");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    DatabaseReference neue = mRef.child(username + "_" + chatWith_id).child("000");
                    neue.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()) {
                                newMessage.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        textViewFullName.setText(user.getFull_name());
        //textViewUserId.setText(user.getFull_name());

        return listViewItem;


    }
}

