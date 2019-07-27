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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText username_tv;
    private TextInputEditText password_tv;
    private Button login;

    private TextView registration_tv;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent (getApplicationContext(),MoiveActivity.class));
        }
        ImageView logo = (ImageView)findViewById(R.id.logoTV) ;
        Animation logoMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.logoanimation);
        logo.startAnimation(logoMoveAnimation);
        username_tv = (TextInputEditText) findViewById(R.id.userIDSignIn);
        password_tv = (TextInputEditText) findViewById(R.id.passwordSignIn);
        registration_tv = findViewById(R.id.registration);
        Animation MoveAnimation = AnimationUtils.loadAnimation(this, R.anim.editanime);
    //    username_tv.startAnimation(MoveAnimation);
    //    password_tv.startAnimation(MoveAnimation);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        login = findViewById(R.id.Login_signin);
        login.startAnimation(MoveAnimation);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username_tv.getText().toString().trim();
                String mPassword = password_tv.getText().toString().trim();
                if (TextUtils.isEmpty(mUsername)) {
                    username_tv.setError("Requrie Field");
                    return;
                }
                if (TextUtils.isEmpty(mPassword)) {
                    password_tv.setError("Requrie Field");
                    return;
                }
                mDialog.setMessage("processing");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(mUsername, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "done  ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MoiveActivity.class));

                        } else if (!task.isSuccessful()) {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        registration_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistraionActivity.class));
            }
        });

    }
}
