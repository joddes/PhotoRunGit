package com.example.janda.photorun.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.janda.photorun.HomeActivity;
import com.example.janda.photorun.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public static Intent makeIntent(HomeActivity homeActivity) {
        return new Intent(homeActivity, LoginActivity.class);
    }
}
