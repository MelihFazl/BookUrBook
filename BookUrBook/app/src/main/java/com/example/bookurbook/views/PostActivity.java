package com.example.bookurbook.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bookurbook.R;
import com.example.bookurbook.models.Post;

public class PostActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        Post post = (Post) intent.getExtras().getSerializable("post");
        TextView title =  findViewById(R.id.title);
        title.setText(post.getTitle());
    }
}