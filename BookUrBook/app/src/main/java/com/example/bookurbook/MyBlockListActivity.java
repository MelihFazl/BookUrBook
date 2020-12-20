package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookurbook.R;
import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;

import java.util.ArrayList;

public class MyBlockListActivity extends AppCompatActivity {
    private User currentUser;
    private RecyclerView blockList;

    private BlockedUsersAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        //toolbar = findViewById(R.id.toolbarblocklist);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("My Blocklist");
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blocklist);
        setProperties();
    }

    public void setProperties()
    {
        this.blockList = findViewById(R.id.blockList);
        adapter = new BlockedUsersAdapter(MyBlockListActivity.this, currentUser.getBlockedUsers(), currentUser);
        blockList.setAdapter(adapter);
        blockList.setLayoutManager(new LinearLayoutManager(this));
    }
    public void add() //silinecek
    {


        //blockedUsers =  currentUser.getBlockedUsers().getUserArray(); //userliste eklendi silincek
       // blockedUsers =  currentUser.getBlockedUsers().getUserArray(); //userliste eklendi silincek

    }

    @Override
    public void onBackPressed() {
        Intent pass = new Intent(MyBlockListActivity.this, SettingsActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }
}