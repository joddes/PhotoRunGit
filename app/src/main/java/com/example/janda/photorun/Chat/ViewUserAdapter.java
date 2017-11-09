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

import com.bumptech.glide.Glide;
import com.example.janda.photorun.R;
import com.example.janda.photorun.models.Photorun;
import com.example.janda.photorun.models.User;
import com.example.janda.photorun.models.ViewProfile;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class ViewUserAdapter extends ArrayAdapter<User> {


    private Activity context;

    String neu, pictureUrl;
    int friends;

    private List<User> userList;
    private FirebaseStorage mStorage;
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
        final View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        //TextView textViewUserId = (TextView) listViewItem.findViewById(R.id.textViewUserId);
        final TextView textViewFullName = (TextView) listViewItem.findViewById(R.id.textViewFullName);
        final ImageView newMessage = (ImageView) listViewItem.findViewById(R.id.newMessages);
        final ImageView mProfilePicture = (ImageView) listViewItem.findViewById(R.id.logo);
        //mTitlePicture = (ImageView) findViewById(R.id.user_background_photo);
        mStorage = FirebaseStorage.getInstance();
        final User user = userList.get(position);
        final String chatWith_id = user.getUser_id();
        final String username = mAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("messages");
        FirebaseDatabase.getInstance().getReference().child("User").child(chatWith_id).child("profileImageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                pictureUrl = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        try{
            //String profileimage = user.getProfileimageUrl();

            StorageReference profileStore = mStorage.getReferenceFromUrl(pictureUrl);

            Glide.with((getContext())).using(new FirebaseImageLoader()).load(profileStore).into(mProfilePicture);
            //Glide.with(getApplicationContext()).load("gs://photorun-3f474.appspot.com/profile_images/NPhoue6JzZRJbtkGUNJyeoKP8QF2").into(mProfilePicture);
        }catch (IllegalArgumentException e){
            String defaultRefUrl = "https://firebasestorage.googleapis.com/v0/b/photorun-3f474.appspot.com/o/profile_images%2Fprofile_photo.png?alt=media&token=38a41782-c029-45f3-9bb9-71d0580e8818";
            StorageReference defaultImage = mStorage.getReferenceFromUrl(defaultRefUrl);

            Glide.with((getContext())).using(new FirebaseImageLoader()).load(defaultImage).into(mProfilePicture);

        }
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    DatabaseReference neue = mRef.child(username + "_" + chatWith_id).child("000");
                    neue.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("newMessages")) {
                                newMessage.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(ViewUserList.swatch ==0) {
                        if(dataSnapshot.hasChild(username + "_" + chatWith_id)){
                            textViewFullName.setText(user.getFull_name());

                            try{
                                //String profileimage = user.getProfileimageUrl();

                                StorageReference profileStore = mStorage.getReferenceFromUrl(pictureUrl);

                                Glide.with((getContext())).using(new FirebaseImageLoader()).load(profileStore).into(mProfilePicture);
                                //Glide.with(getApplicationContext()).load("gs://photorun-3f474.appspot.com/profile_images/NPhoue6JzZRJbtkGUNJyeoKP8QF2").into(mProfilePicture);
                            }catch (IllegalArgumentException e){
                                String defaultRefUrl = "https://firebasestorage.googleapis.com/v0/b/photorun-3f474.appspot.com/o/profile_images%2Fprofile_photo.png?alt=media&token=38a41782-c029-45f3-9bb9-71d0580e8818";
                                StorageReference defaultImage = mStorage.getReferenceFromUrl(defaultRefUrl);

                                Glide.with((getContext())).using(new FirebaseImageLoader()).load(defaultImage).into(mProfilePicture);

                            }
                        }else{
                            listViewItem.setVisibility(View.GONE);
                            userList.remove(user);
                        }
                    }else {
                        textViewFullName.setText(user.getFull_name());

                        try{
                            //String profileimage = user.getProfileimageUrl();

                            StorageReference profileStore = mStorage.getReferenceFromUrl(pictureUrl);

                            Glide.with((getContext())).using(new FirebaseImageLoader()).load(profileStore).into(mProfilePicture);
                            //Glide.with(getApplicationContext()).load("gs://photorun-3f474.appspot.com/profile_images/NPhoue6JzZRJbtkGUNJyeoKP8QF2").into(mProfilePicture);
                        }catch (IllegalArgumentException e){
                            String defaultRefUrl = "https://firebasestorage.googleapis.com/v0/b/photorun-3f474.appspot.com/o/profile_images%2Fprofile_photo.png?alt=media&token=38a41782-c029-45f3-9bb9-71d0580e8818";
                            StorageReference defaultImage = mStorage.getReferenceFromUrl(defaultRefUrl);

                            Glide.with((getContext())).using(new FirebaseImageLoader()).load(defaultImage).into(mProfilePicture);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //textViewUserId.setText(user.getFull_name());

        return listViewItem;


    }
}

