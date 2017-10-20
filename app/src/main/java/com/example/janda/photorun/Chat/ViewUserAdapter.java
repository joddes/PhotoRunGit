package com.example.janda.photorun.Chat;

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
import com.example.janda.photorun.models.User;

import java.util.List;

public class ViewUserAdapter extends ArrayAdapter<User> {


    private Activity context;



    private List<User> userList;


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

        User user = userList.get(position);

        textViewFullName.setText(user.getFull_name());
        //textViewUserId.setText(user.getFull_name());

        return listViewItem;


    }
}

