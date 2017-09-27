package com.example.janda.photorun;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends Activity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    //private EditText editTextUsername;
    private EditText editTextPassword;

    //private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.btn_register);

        editTextEmail = (EditText) findViewById(R.id.input_email);
        //editTextUsername = (EditText) findViewById(R.id.input_username);
        editTextPassword = (EditText) findViewById(R.id.input_password);

        buttonRegister.setOnClickListener(this);

        //Button animation
        final ImageButton btnstart = (ImageButton) findViewById(R.id.login_btn);
        final TextView btnsignup = (TextView) findViewById(R.id.link_signup);
        final TextView btnlogin = (TextView) findViewById(R.id.link_login);
        final ImageView btnlogo = (ImageView) findViewById(R.id.logo);
        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        final Animation logoanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_animation);
        final Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
        final Animation slide_left_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_out);
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
        final Animation slide_right_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_out);
        final Animation slide_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);

        btnstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                findViewById(R.id.login).startAnimation(slide_up);
                findViewById(R.id.logo).startAnimation(logoanim);
                findViewById(R.id.login).setVisibility(View.VISIBLE);
                findViewById(R.id.login_btn).setVisibility(View.GONE);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                findViewById(R.id.register).startAnimation(slide_left);
                findViewById(R.id.register).setVisibility(View.VISIBLE);
                findViewById(R.id.login).startAnimation(slide_left_out);
                findViewById(R.id.login).setVisibility(View.GONE);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                findViewById(R.id.login).startAnimation(slide_right);
                findViewById(R.id.login).setVisibility(View.VISIBLE);
                findViewById(R.id.register).startAnimation(slide_right_out);
                findViewById(R.id.register).setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
    }





    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        //String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }
        /*if(TextUtils.isEmpty(username)) {
            //username is empty
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            //stopping the funtion
            return;
        }*/
        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

       // progressDialog.setMessage("Registrating user ..");
       // progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(MainActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

        }


    @Override
    public void onClick(View view) {

        if(view == buttonRegister){
            registerUser();
        }



    }
}
