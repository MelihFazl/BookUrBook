package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookurbook.R;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;

import java.util.ArrayList;

public class MyBlockListActivity extends AppCompatActivity {
    private User currentUser;
    private RecyclerView blockList;
    private TextView username;
    private ImageView profile_img;
    private ImageButton blockButton;
    private ArrayList<User> blockedUsers;
    private BlockedUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blocklist);
        setProperties();
    }

    public void setProperties()
    {
        add();
        this.blockList = findViewById(R.id.blockList);
        adapter = new BlockedUsersAdapter(getBaseContext(), blockedUsers, currentUser);
        blockList.setAdapter(adapter);
        blockList.setLayoutManager(new LinearLayoutManager(this));

    }
    public void add()
    {
        currentUser = new RegularUser("miri","miray.ayerdem@ug.bilkent.edu.tr", R.drawable.userimg);
        currentUser.getBlockedUsers().addUser(new RegularUser("kaan","kaan.tek@ug.bilkent.edu.tr", R.drawable.bru4));
        currentUser.getBlockedUsers().addUser(new RegularUser("kerem","kerem.sahin@ug.bilkent.edu.tr", R.drawable.bru3));
        currentUser.getBlockedUsers().addUser(new RegularUser("melih","melih.keskin@ug.bilkent.edu.tr", R.drawable.bru2));
        currentUser.getBlockedUsers().addUser(new RegularUser("ferhat","ferhat.korkmaz@ug.bilkent.edu.tr", R.drawable.user));
        blockedUsers = currentUser.getBlockedUsers().getUserArray();
    }

}