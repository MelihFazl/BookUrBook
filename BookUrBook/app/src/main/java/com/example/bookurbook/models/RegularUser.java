package com.example.bookurbook.models;

import android.widget.ImageView;

import androidx.annotation.Keep;

import java.io.Serializable;


public class RegularUser extends User implements Serializable {
    //constructor
    public RegularUser(String username, String email, ImageView avatar) {
        super(username, email, avatar);
    }
}


