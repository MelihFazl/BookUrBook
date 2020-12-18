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
    //Toolbar toolbar;
    RecyclerView recyclerView;
    FilterScreenView filterScreenView;
    SearchView searchView;
    Button LtoHpriceButton;
    Button HtoLpriceButton;
    Button AtoZbutton;
    Button ZtoAbutton;
    Button resetButton;
    ImageButton filterButton;
    PostListAdapter postListAdapter;
    PostList postList;
    private FirebaseFirestore db;
    private User currentUser;
    private User currentPostOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        // postList = new ArrayList<>();                    delete later?
        //add();  // method for adding posts from database ?

        // list from the model class

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


        // later do all these operations with database
       //postList.addPost(new Post("great book", "big Java", "Bilkent University", "CS", 30, null, new RegularUser("Kaan", "mail", null)));



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


        postListAdapter = new PostListAdapter(this, postList);
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // filterScreenView = new FilterScreenView(postListAdapter);            may deletee?

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

    /*@Override
    public void onBackPressed() {
        Intent pass = new Intent(PostListActivity.this, MainMenuActivity.class);
        pass.putExtra("user", currentUser);
        startActivity(pass);
    }*/


    public void openFilterWindow() {
        FilterScreenView filterScreen = new FilterScreenView();    //deleted the parameter constructor?
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
    public void onBackPressed() {
        Intent pass = new Intent(PostListActivity.this, MainMenuActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    public void filterThePosts(String uni, String course, int lowPrice, int highPrice) {
            postListAdapter.filterResults(uni, course, lowPrice, highPrice);

    }
}