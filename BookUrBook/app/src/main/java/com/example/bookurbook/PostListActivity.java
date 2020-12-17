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


import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;

import java.util.ArrayList;


// class for the Post List activity
public class PostListActivity extends AppCompatActivity {

    // variables
    //Toolbar toolbar;
    RecyclerView recyclerView;
    SearchView searchView;
    Button LtoHpriceButton;
    Button HtoLpriceButton;
    Button AtoZbutton;
    Button ZtoAbutton;
    Button resetButton;
    ImageButton filterButton;
    PostListAdapter postListAdapter;
    PostList postList;    // PostList object?   // but there is no consturctor, is it ok ??

    // ArrayList<PostView> postList;         delete later?

    public PostListActivity()
    {
        // is this constructor necessary?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        // postList = new ArrayList<>();                    delete later?
        //add();  // method for adding posts from database ?

        // list from the model class
        postList = new PostList();

        // later do all these operations with database
        postList.addPost(new Post("great book", "big Java", "Bilkent University", "CS", 30, null, new RegularUser("Kaan", "mail", null)));
        postList.addPost(new Post("bad book", "discrete book", "Bilkent University", "CS", 20, null, new RegularUser("kerem", "mail", null)));
        postList.addPost(new Post("hard book", "muscles", "Hacettepe University", "CS", 50, null, new RegularUser("ata", "mail", null)));

        searchView = findViewById(R.id.search_id);
        recyclerView = findViewById(R.id.recycler_id);
        //toolbar = findViewById(R.id.toolbar);
        LtoHpriceButton = findViewById(R.id.LtoH_price_button);
        HtoLpriceButton = findViewById(R.id.HtoL_price_button);
        AtoZbutton = findViewById(R.id.AtoZ_button);
        ZtoAbutton = findViewById(R.id.ZtoA_button);
        resetButton = findViewById(R.id.reset_button);
        filterButton = findViewById(R.id.filterButton);

        //setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // implement this later so that it goes back to the previous screen


        postListAdapter = new PostListAdapter(this, postList);   // had to make it final, maybe change it later?
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search(postListAdapter); // method for searching

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.menu, menu); */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.a)
        {
            // create post screen i aç
        }*/
        return true;
    }
    // method for adding posts to the post list

    public void add()    // later change this method to add posts from database ???
    {
        // napacaz bunu
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

    @Override
    protected void onStart() {
        //After waiting 10 seconds, the activity goes to the post screen in order to show what has been done so far by Kerem.
        super.onStart();
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent pass = new Intent(PostListActivity.this, PostActivity.class);
                startActivity(pass);

            }
        }.start();
    }
}