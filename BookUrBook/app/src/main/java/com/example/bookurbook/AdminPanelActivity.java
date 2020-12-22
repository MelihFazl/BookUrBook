package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;

import java.util.ArrayList;

public class AdminPanelActivity extends AppCompatActivity {
    //properties
    private RecyclerView recyclerView;
    private ReportsAdapter adapter;
    private User currentUser;
    private ArrayList<User> reportedUsers;
    Toolbar toolbar;

    //method code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        toolbar = findViewById(R.id.toolbarblocklist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reported Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setProperties();

    }
    public void setProperties()
    {
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        this.recyclerView = findViewById(R.id.reportList);
       // adapter = new ReportsAdapter(getBaseContext(), reportedUsers);
       // recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onBackPressed()
    {
        Intent pass = new Intent(AdminPanelActivity.this, MainMenuActivity.class);
        //pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}