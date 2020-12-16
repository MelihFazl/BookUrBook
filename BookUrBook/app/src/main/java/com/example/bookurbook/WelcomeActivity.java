package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.widget.ImageView;


import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.Serializable;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private User currentUser;

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
                    if(documentSnapshot.getString("avatar") != null)
                    {
                        currentUser.setAvatar(documentSnapshot.getString("avatar"));
                    }
                    else
                        currentUser.setAvatar("https://firebasestorage.googleapis.com/v0/b/bookurbook-a02e4.appspot.com/o/images%2Fprofile_pictures%2Fdefault.jpg?alt=media&token=a54505f6-0d24-40cd-a626-e39a655254c6");
                    Intent pass = new Intent(WelcomeActivity.this, MainMenuActivity.class);
                    pass.putExtra("user", currentUser);
                    startActivity(pass);
                }
            });
        }
        else
        {
            new CountDownTimer(3000, 1000)
            {
                public void onTick(long millisUntilFinished) { }
                public void onFinish()
                {
                    Intent pass = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(pass);
                }
            }.start();

        }

    }

}
