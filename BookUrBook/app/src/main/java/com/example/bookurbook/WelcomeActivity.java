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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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
        if(auth.getCurrentUser() != null)
        {
            db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getBoolean("admin"))
                        currentUser = new Admin(documentSnapshot.getString("username"), documentSnapshot.getString("email"), null);
                    else
                        currentUser = new RegularUser(documentSnapshot.getString("username"), documentSnapshot.getString("email"), null);

                }
            });
        }

        new CountDownTimer(4444, 1111)
        {
            public void onTick(long millisUntilFinished){}
            public void onFinish()
            {
                if (auth.getCurrentUser() == null)
                {
                    Intent pass = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(pass);
                }
                else
                {
                    Intent pass = new Intent(WelcomeActivity.this, MainMenuActivity.class);
                    pass.putExtra("user", currentUser);
                    startActivity(pass);
                }

            }
        } .start();
    }

}