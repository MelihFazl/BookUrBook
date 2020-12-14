package com.example.bookurbook.controllers;

import androidx.annotation.NonNull;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDatabaseConnection
{
    private User user;
    private DatabaseReference db;
    private FirebaseAuth auth;
    public UserDatabaseConnection(User user)
    {
        db = FirebaseDatabase.getInstance().getReference( );
        auth = FirebaseAuth.getInstance();
        this.user = user;
        db.addValueEventListener(new ValueEventListener()
        {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.hasChild("/users/" + auth.getCurrentUser().getUid()))
                    {
                        user.setUsername(snapshot.child("/users/" + auth.getCurrentUser().getUid() +"/username").getValue(String.class));
                        user.setEmail(snapshot.child("/users/" + auth.getCurrentUser().getUid() +"/email").getValue(String.class));
                        user.setBanned(snapshot.child("/users/" + auth.getCurrentUser().getUid() +"/banned").getValue(Boolean.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

