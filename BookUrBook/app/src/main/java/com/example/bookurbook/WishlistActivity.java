package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        Toolbar toolbar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        toolbar = findViewById(R.id.wishlistToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Wishlist");
    }
}