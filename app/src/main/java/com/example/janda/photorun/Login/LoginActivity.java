package com.example.janda.photorun.Login;

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

import com.example.janda.photorun.Photorun.CreateRun;
import com.example.janda.photorun.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

        private Button buttonRegister;
        private EditText editTextEmaillogin;
        //private EditText editTextUsername;
        private EditText editTextPassword;
        private Button buttonSignIn;
        private EditText editTextEmail;
        private EditText editTextPasswordlogin;
        public static int logVar=0;



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
                final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                final Animation logoanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_animation);
                final Animation logoanim_back = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_animation_back);
                final Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
                final Animation slide_left_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_out);
                final Animation slide_right_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_out);
                final Animation slide_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);

                //--Button Action Key
                btnstart.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                                findViewById(R.id.login).startAnimation(slide_up);
                                findViewById(R.id.logo).startAnimation(logoanim);
                                findViewById(R.id.login).setVisibility(View.VISIBLE);
                                findViewById(R.id.login_btn).setVisibility(View.GONE);
                                logVar=1;
                        }
                });

                //--Button register now
                btnsignup.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                                findViewById(R.id.register).startAnimation(slide_left);
                                findViewById(R.id.register).setVisibility(View.VISIBLE);
                                findViewById(R.id.login).startAnimation(slide_left_out);
                                findViewById(R.id.login).setVisibility(View.GONE);
                                logVar=2;
                        }
                });

                //--Button back to login
                btnlogin.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                                findViewById(R.id.login).startAnimation(slide_right);
                                findViewById(R.id.login).setVisibility(View.VISIBLE);
                                findViewById(R.id.register).startAnimation(slide_right_out);
                                findViewById(R.id.register).setVisibility(View.GONE);
                                logVar=1;
                        }
                });

                if(firebaseAuth.getCurrentUser() != null){
                        //close this activity
                        finish();
                        //opening profile activity
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }

                editTextEmaillogin = (EditText) findViewById(R.id.input_email_login);
                editTextPasswordlogin = (EditText) findViewById(R.id.input_password_login);
                buttonSignIn = (Button) findViewById(R.id.btn_login);

                buttonSignIn.setOnClickListener(this);

        }

        private void userLogin(){
                String email = editTextEmaillogin.getText().toString().trim();
                String password  = editTextPasswordlogin.getText().toString().trim();


                //checking if email and passwords are empty
                if(TextUtils.isEmpty(email)){
                        Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
                        return;
                }

                if(TextUtils.isEmpty(password)){
                        Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
                        return;
                }

                //if the email and password are not empty
                //displaying a progress dialog



                //logging in the user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                        //if the task is successfull
                                        if(task.isSuccessful()){
                                                //start the profile activity

                                                finish();
                                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                        }
                                        else{
                                                Toast.makeText(LoginActivity.this,"Wrong password entered.",Toast.LENGTH_LONG).show();
                                        }
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
                                                Toast.makeText(LoginActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                        }else{
                                                //display some message here
                                                Toast.makeText(LoginActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                                        }
                                }
                        });

        }


        @Override
        public void onClick(View view) {

                if(view == buttonRegister){
                        registerUser();
                }
                if(view == buttonSignIn){
                        userLogin();
                }

        }

        @Override
        public void onBackPressed() {
                final ImageButton btnstart = (ImageButton) findViewById(R.id.login_btn);
                final TextView btnsignup = (TextView) findViewById(R.id.link_signup);
                final TextView btnlogin = (TextView) findViewById(R.id.link_login);
                final ImageView btnlogo = (ImageView) findViewById(R.id.logo);
                final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                final Animation logoanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_animation);
                final Animation logoanim_back = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_animation_back);
                final Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
                final Animation slide_left_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_out);
                final Animation slide_right_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_out);
                final Animation slide_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
                if(logVar==0){
                        
                }if(logVar==1){
                        findViewById(R.id.login).startAnimation(slide_down);
                        findViewById(R.id.logo).startAnimation(logoanim_back);
                        findViewById(R.id.login).setVisibility(View.GONE);
                        findViewById(R.id.login_btn).setVisibility(View.VISIBLE);
                        logVar=0;
                }if(logVar==2){
                        findViewById(R.id.login).startAnimation(slide_right);
                        findViewById(R.id.login).setVisibility(View.VISIBLE);
                        findViewById(R.id.register).startAnimation(slide_right_out);
                        findViewById(R.id.register).setVisibility(View.GONE);
                        logVar=1;
                }
        }


}
