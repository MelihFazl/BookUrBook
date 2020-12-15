package com.example.bookurbook.controllers;

import androidx.annotation.NonNull;

import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDatabaseConnection
{
    private User user;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private String a;
    public UserDatabaseConnection(User user)
    {
        this.user = user;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserDatabaseConnection.this.user.setEmail((String)documentSnapshot.get("email"));
                UserDatabaseConnection.this.user.setBanned((boolean)documentSnapshot.get("banned"));
                UserDatabaseConnection.this.user.setUsername((String)documentSnapshot.get("username"));
                a = (String)documentSnapshot.get("username");
                System.out.println(a + "iç");
            }
        });
                System.out.println(a + "DIŞ");

    }

}

