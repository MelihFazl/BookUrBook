package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.widget.ImageView;


import com.example.bookurbook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        new CountDownTimer(4444, 1111)
        {
            public void onTick(long millisUntilFinished){}
            public void onFinish()
            {
                if (auth.getCurrentUser() == null)
                {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                else
                {
                    Intent pass = new Intent(WelcomeActivity.this, MainMenuActivity.class);

                    //System.out.println("NOLUYOLAN");
                    //System.out.println(currentUser.getUsername());
                    //pass.putExtra("user", currentUser);
                    startActivity(pass);
                }

            }
        } .start();
    }

}