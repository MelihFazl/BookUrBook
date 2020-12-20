package com.example.bookurbook;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


// class for the Post List activity
public class PostListActivity extends AppCompatActivity implements FilterScreenView.FilterScreenListener {

    // variables
    Toolbar toolbar;
    RecyclerView recyclerView;
    SearchView searchView;
    Button LtoHpriceButton;
    Button HtoLpriceButton;
    Button AtoZbutton;
    Button ZtoAbutton;
    Button resetButton;
    ImageButton filterButton;
    ImageButton createPostButton;
    PostListAdapter postListAdapter;
    PostList postList;
    private FirebaseFirestore db;
    private User currentUser;
    private User currentPostOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);


        // for database purposes
        db = FirebaseFirestore.getInstance();
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");
        postList = (PostList) getIntent().getSerializableExtra("postlist");
        for(int i = 0; postList.getPostArray().size() > i; i++)
        {
            System.out.println(postList.getPostArray().get(i).getOwner().getEmail());
        }
        System.out.println("CURRENT USER: " + currentUser.getUsername());



        createPostButton = findViewById(R.id.createPostButton);

        searchView = findViewById(R.id.search_id);
        recyclerView = findViewById(R.id.recycler_id);
        toolbar = findViewById(R.id.toolbar);
        LtoHpriceButton = findViewById(R.id.LtoH_price_button);
        HtoLpriceButton = findViewById(R.id.HtoL_price_button);
        AtoZbutton = findViewById(R.id.AtoZ_button);
        ZtoAbutton = findViewById(R.id.ZtoA_button);
        resetButton = findViewById(R.id.reset_button);
        filterButton = findViewById(R.id.filterButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // implement this later so that it goes back to the previous screen


        postListAdapter = new PostListAdapter(this, postList, currentUser);   // had to make it final, maybe change it later?
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        search(postListAdapter); // method for searching

        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostListActivity.this, CreatePostActivity.class);
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("postlist", postList);
                intent.putExtra("fromPostList", true);
                startActivity(intent);
            }
        });

        LtoHpriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapter.sort(view);
            }
        });

        HtoLpriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapter.sort(view);
            }
        });

        AtoZbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapter.sort(view);
            }
        });

        ZtoAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postListAdapter.sort(view);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                postListAdapter.sort(view);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterWindow();
            }
        });


    }


    public void openFilterWindow() {
        FilterScreenView filterScreen = new FilterScreenView();
        filterScreen.show(getSupportFragmentManager(), "example filter");
    }


    // method for searching by a keyword from the list
    public void search(PostListAdapter adp)
    {
        final PostListAdapter adapter = adp;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    // this does not work ????
    @Override
    public void onBackPressed() {
        Intent pass = new Intent(PostListActivity.this, MainMenuActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    @Override
    public void filterThePosts(String uni, String course, int lowPrice, int highPrice) {

    }
}