package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MyChatsView extends AppCompatActivity {

    // variables

    RecyclerView recyclerView;
    MyChatsAdapter myChatsAdapter;
    MyChatsActivity myChatsActivity;
    ArrayList<String> emptyArraylist;  // delete this after database connection

    // add Firebase and User properties maybe?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats_view);

        // do some firebase operations just like in PostListActicty??
        myChatsActivity = new MyChatsActivity();


        recyclerView =findViewById(R.id.my_chats_recycler_id);
        emptyArraylist = new ArrayList<>(); // delete this after database connection

        myChatsAdapter = new MyChatsAdapter(this, myChatsActivity.chatList);
        recyclerView.setAdapter(myChatsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}