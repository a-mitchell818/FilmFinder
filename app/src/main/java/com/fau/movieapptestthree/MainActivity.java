package com.fau.movieapptestthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muddzdev.styleabletoast.StyleableToast;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //variable declarations
    private FirebaseAuth mFireBaseAuth;
    private CheckBox checkBox;
    private EditText emailId, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assign variables
        final Button signUpbtn = findViewById(R.id.signUpbtn);
        final Button signInbtn = findViewById(R.id.signInbtn);
        mFireBaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailEditText);
        pwd = findViewById(R.id.passwordEditText);
        TextView signInTextView = findViewById(R.id.signInTextView);
        TextView signUpTextView = findViewById(R.id.signUpTextView);
        checkBox = findViewById(R.id.checkBox);

        //Button Animation
        final Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.bounce);

        //Password Hide or Show user
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    //Show Password
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //Hide Password
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInbtn.setVisibility(View.VISIBLE);
                signUpbtn.setVisibility(View.INVISIBLE);
            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInbtn.setVisibility(View.INVISIBLE);
                signUpbtn.setVisibility(View.VISIBLE);
            }
        });

        //Creates an account for user authenticated through Firebase with conditional to checks
        // if you are signing up properly
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String password = pwd.getText().toString();
                //If the user email is not entered in check
                if (email.isEmpty()) {
                    emailId.setError("Please provide your email address");
                    emailId.setText("");
                    emailId.requestFocus();
                    //If the user password is not entered in check
                } else if (password.isEmpty()) {
                    pwd.setError("Please provide your password");
                    pwd.setText("");
                    pwd.requestFocus();
                    //If the user email and password are entered check
                } else {
                    mFireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //If user does not successfully authenticate as a new user wih Firebase
                            if (!task.isSuccessful()) {
                                StyleableToast.makeText(MainActivity.this,"Sign Up Unsuccessful \n Please Try Again",R.style.myToastNegative).show();
                                MainActivity.this.finish();
                                MainActivity.this.startActivity(MainActivity.this.getIntent());
                                //If new user successfully authenticate wih Firebase bring to settings activity
                                //If new user successfully authenticate wih Firebase bring to Dashboard activity
                            } else {
                                StyleableToast.makeText(MainActivity.this,"Welcome to Film Finder",R.style.myToastPositive).show();
                                //Intent inToSettings = new Intent(getApplicationContext(), Settings.class);
                                //startActivity(inToSetting);
                                finish();
                                StyleableToast.makeText(MainActivity.this,"Welcome to Film Finder",R.style.myToastPositive).show();
                                Intent inToDashboard = new Intent(getApplicationContext(), Main3Activity.class);
                                startActivity(inToDashboard);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        //Login button method for user & account access checks
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String password = pwd.getText().toString();
                //If the user email is not entered in check
                if (email.isEmpty()) {
                    emailId.setError("Please enter your email address");
                    emailId.requestFocus();
                    //If the user password is not entered in check
                } else if (password.isEmpty()) {
                    pwd.setError("Please enter your password");
                    pwd.requestFocus();
                    //If the user email and password are entered check
                } else {
                    mFireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //If user does not successfully authenticate as a existing user wih Firebase
                            if (!task.isSuccessful()) {
                                StyleableToast.makeText(MainActivity.this,"Login Unsuccessful \n Please Try Again",R.style.myToastNegative).show();
                                MainActivity.this.finish();
                                MainActivity.this.startActivity(MainActivity.this.getIntent());
                                //If user does successfully authenticate as a existing user wih Firebase bring them to the dashboard
                            } else {
                                StyleableToast.makeText(MainActivity.this,"Welcome to Film Finder",R.style.myToastPositive).show();
                                Intent inToDashboard = new Intent(getApplicationContext(), Main3Activity.class);
                                startActivity(inToDashboard);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        if (currentUser != null) {
            //Go to Dashboard
            Intent inToDashboard = new Intent(getApplicationContext(), Main3Activity.class);
            startActivity(inToDashboard);
            finish();
        }
    }



}
