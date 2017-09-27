package com.example.janda.photorun.Photorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.janda.photorun.R;

public class ViewRuns extends AppCompatActivity {

    private Button goToCreateRun;

    private ListView PhotoRunList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_runs);

        goToCreateRun = (Button) findViewById(R.id.goToCreateRun);

        PhotoRunList = (ListView) findViewById(R.id.PhotoRunList);

    }

    public void navigateToCreateRun(){
        goToCreateRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreateRun = new Intent(getApplicationContext(), CreateRun.class);
                startActivity(toCreateRun);
            }
        });
    }

    public void listRuns(){

    }
}
