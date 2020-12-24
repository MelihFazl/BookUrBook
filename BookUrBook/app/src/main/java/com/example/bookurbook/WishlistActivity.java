package com.example.bookurbook;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    //properties
    Toolbar toolbar;
    RecyclerView recyclerView;
    WishlistAdapter adapter;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        toolbar = findViewById(R.id.wishlistToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Wishlist");
        setProperties();
    }

    public void setProperties(){
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");

<<<<<<< Updated upstream

        recyclerView = findViewById(R.id.wishList);
        adapter = new WishlistAdapter(WishlistActivity.this, currentUser.getWishList(), currentUser);
=======
        //wishlist = (PostList) getIntent().getSerializableExtra("postlist");
        //recyclerView = findViewById(R.id.wishList);
        adapter = new WishlistAdapter(WishlistActivity.this, wishlist.getPostArray(), currentUser);
>>>>>>> Stashed changes
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onBackPressed()
    {
        Intent pass = new Intent(WishlistActivity.this, MainMenuActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}