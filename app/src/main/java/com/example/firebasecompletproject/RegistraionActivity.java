package com.example.firebasecompletproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistraionActivity extends AppCompatActivity {
    private TextInputEditText username_tv;
    private TextInputEditText Email_tv;
    private TextInputEditText password_tv;
    private TextInputEditText password2_tv;
    private Button registration_tv;
    private TextView login_tv;
    public String mUsername;
    public String mEmail;
    public String mPassword;
    public String mPassword2;


    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);
        ImageView logo = (ImageView)findViewById(R.id.logoTV2) ;
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.logoanimation);
        logo.startAnimation(logoMoveAnimation);
        username_tv = (TextInputEditText) findViewById(R.id.userIDTextInputEditText);
        Email_tv = (TextInputEditText) findViewById(R.id.EmailDTextInputEditText);
        password_tv = (TextInputEditText) findViewById(R.id.password_regis);
        password2_tv = (TextInputEditText) findViewById(R.id.password_regis2);
        mDialog = new ProgressDialog(this);
        // mAuth=  FirebaseAuth.getInstance();
        registration_tv = findViewById(R.id.registration);
        registration_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = username_tv.getText().toString().trim();
                mEmail = Email_tv.getText().toString().trim();
                mPassword = password_tv.getText().toString().trim();
                mPassword2 = password2_tv.getText().toString().trim();


                if (TextUtils.isEmpty(mUsername)) {
                    username_tv.setError("Requrie Field");
                    return;
                } else if (TextUtils.isEmpty(mEmail)) {
                    Email_tv.setError("Requrie Field");
                    return;
                } else if (TextUtils.isEmpty(mPassword) && TextUtils.isEmpty(mPassword2)) {
                    password_tv.setError("Requrie Field");
                    password2_tv.setError("Requrie Field");
                    return;
                } else if (mPassword.length() <= 8) {
                    password_tv.setError("Must exceed 8 characters!");
                } else if (!mPassword.equals(mPassword2)) {
                    password2_tv.setError("Passwords didnit match");
                    password_tv.setError("Passwords didnit match");
                    Toast.makeText(RegistraionActivity.this, "Passwords didnit match", Toast.LENGTH_SHORT).show();

                    return;
                } else
                    mDialog.setMessage("processing");
                mDialog.show();


                mAuth.getInstance().createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.setMessage("processing");
                            mDialog.show();
                            finish();
                            Toast.makeText(getApplicationContext(), "done  signin with new account", Toast.LENGTH_SHORT).show();

                        } else if (!task.isSuccessful()) {
                            mDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }
        });
        login_tv = findViewById(R.id.Login);
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
