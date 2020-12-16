package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        new CountDownTimer(4444, 1111)
        {
            public void onTick(long millisUntilFinished){}
            public void onFinish()
            {

                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        } .start();
    }
}