package com.example.bookurbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


// class for the Post List activity
public class MainActivity extends AppCompatActivity {

    // variables
    Toolbar toolbar;
    RecyclerView recyclerView;
    SearchView searchView;
    Button LtoHpriceButton;
    Button HtoLpriceButton;
    Button AtoZbutton;
    Button ZtoAbutton;
    Button resetButton;

    ArrayList<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        add();  // method for adding posts from database ?

        searchView = findViewById(R.id.search_id);
        recyclerView = findViewById(R.id.recycler_id);
        toolbar = findViewById(R.id.toolbar);
        LtoHpriceButton = findViewById(R.id.LtoH_price_button);
        HtoLpriceButton = findViewById(R.id.HtoL_price_button);
        AtoZbutton = findViewById(R.id.AtoZ_button);
        ZtoAbutton = findViewById(R.id.ZtoA_button);
        resetButton = findViewById(R.id.reset_button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   // implement this later so that it goes back to the previous screen


        final PostListAdapter postListAdapter = new PostListAdapter(this, postList);
        recyclerView.setAdapter(postListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search(postListAdapter); // method for searching

        LtoHpriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorter(postListAdapter, view);
            }
        });

        HtoLpriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorter(postListAdapter, view);
            }
        });

        AtoZbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorter(postListAdapter, view);
            }
        });

        ZtoAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorter(postListAdapter, view);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorter(postListAdapter, view);
            }
        });


    }


    // method for adding posts to the post list
    public void add()    // later change this method to add posts from database ???
    {
        postList.add(new Post(R.drawable.scr_fourth, "BigJava Objects", "SELLER: Kaan Tek", "100", "Bilkent", "Cs" ));
        postList.add(new Post(R.drawable.scr_fifth, "Math132 discrete", "SELLER: Ferhat Korkmaz", "75", "Bilkent", "math" ));
        postList.add(new Post(R.drawable.scr_sixth, "BigJava More Objects", "SELLER: Kerem Şahin", "85", "Ankara uni", "Cs" ));
        postList.add(new Post(R.drawable.scr_fifth, "James Stewart Calculus", "SELLER: Miray Ayerdem", "40", "Gazi", "math" ));
        postList.add(new Post(R.drawable.scr_fourth, "Humanity101 Destroyer", "SELLER: Melih Fazıl Keskin", "40", "Gazi", "hum" ));
        postList.add(new Post(R.drawable.scr_sixth, "HIST201 Study Book", "SELLER: Kaan Tek", "85", "Bilkent uni", "HIST" ));
        postList.add(new Post(R.drawable.scr_fifth, "ENG101 articles", "SELLER: Can Yılmaz", "30", "Koç university", "eng" ));
        postList.add(new Post(R.drawable.scr_fourth, "M223 discrete", "SELLER: Kerem Şahin", "90", "METU", "MATH" ));
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

    // method for sorting by price low to high
    public void sorter(PostListAdapter adp, View v)
    {
        PostListAdapter adapter;
        adapter = adp;
        adapter.sort(v);
    }


}