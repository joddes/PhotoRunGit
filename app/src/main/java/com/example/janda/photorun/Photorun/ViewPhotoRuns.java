package com.example.janda.photorun.Photorun;

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
import com.example.janda.photorun.models.Photorun;

import java.util.List;

public class ViewPhotoRuns extends ArrayAdapter<Photorun> {


    private Activity context;



    private List<Photorun> photorunList;


    public ViewPhotoRuns(Activity context, List<Photorun> photorunList){
        super(context, R.layout.layout_photorun_list, photorunList);
        this.context = context;
        this.photorunList = photorunList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_photorun_list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.run_title);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        Photorun photorun = photorunList.get(position);

        textViewTitle.setText(photorun.getTitle());
        textViewDate.setText(photorun.getDate());

        return listViewItem;


    }
}

