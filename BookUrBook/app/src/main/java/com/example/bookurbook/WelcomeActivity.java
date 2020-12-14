package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.widget.ImageView;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference db;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

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
                    currentUser = new RegularUser(null, null, null);
                    System.out.println("NOLUYOLAN");
                    System.out.println(currentUser.getUsername());
                    //pass.putExtra("user", currentUser);
                    startActivity(pass);
                }

            }
        } .start();
    }

}