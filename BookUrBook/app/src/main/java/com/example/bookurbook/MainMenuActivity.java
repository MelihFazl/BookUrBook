package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainMenuActivity extends AppCompatActivity {

    private View topleft;
    private View topright;
    private View botleft;
    private View botright;
    private ImageView wishlist;
    private User currentUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if(getIntent().getSerializableExtra("user") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("user");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("user");
        System.out.println("Main Menu Current User email:  " + currentUser.getEmail());
    }


    public void init()
    {
        topleft = findViewById(R.id.topleft);
        topright = findViewById(R.id.topright);
        botleft = findViewById(R.id.botleft);
        botright = findViewById(R.id.botright);
        wishlist = findViewById(R.id.wishlist);


        topleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, PostListActivity.class));
            }
        });
        topright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, MyChatsActivity.class);
                pass.putExtra("user", currentUser);
                startActivity(pass);
            }
        });

        botleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MyPostsActivity.class));
            }
        });
        botright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, SettingsActivity.class);
                pass.putExtra("user", currentUser);
                startActivity(pass);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, WishlistActivity.class));
            }
        });

    }
}