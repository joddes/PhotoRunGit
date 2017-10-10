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

import com.example.janda.photorun.R;
import com.example.janda.photorun.models.User;

import java.util.List;


/**
 * Created by Tim Seemann on 09.10.2017.
 */

public class ViewAttendeesListAdapter extends ArrayAdapter<User> {


    private Activity context;


    private List<User> userList;


    public ViewAttendeesListAdapter(Activity context, List<User> userList) {
        super(context, R.layout.layout_photorun_list, userList);
        // super(context, R.layout.layout_photorun_list, userList);
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_photorun_list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.run_title);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        User user = userList.get(position);

        textViewTitle.setText(user.getFull_name());
        textViewDate.setText(user.getEmail());

        return listViewItem;


    }
}
