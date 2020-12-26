package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

    //properties
    private User currentUser;
    private RecyclerView blockList;
    private ImageButton homeButton;
    private BlockedUsersAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blocklist);
        //variables
        toolbar = findViewById(R.id.toolbarblocklist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Blocklist");
        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MyBlockListActivity.this, MainMenuActivity.class);
                startIntent.putExtra("currentUser" , currentUser);
                startActivity(startIntent);
            }
        });
        setProperties();
    }
    /**
     * This method will construct the variables of the class
     */
    public void setProperties()
    {
        this.blockList = findViewById(R.id.blockList);
        adapter = new BlockedUsersAdapter(MyBlockListActivity.this, currentUser.getBlockedUsers(), currentUser);
        blockList.setAdapter(adapter);
        blockList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent pass = new Intent(MyBlockListActivity.this, SettingsActivity.class);
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