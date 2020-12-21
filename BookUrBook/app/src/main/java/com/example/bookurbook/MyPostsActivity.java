package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.example.bookurbook.models.UserSpecPostList;
import com.squareup.picasso.Picasso;

public class MyPostsActivity extends AppCompatActivity {

    ImageButton add;
    private Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView img;
    TextView username, userType;
    MyPostsAdapter adp;
    PostList postList;
    UserSpecPostList userSpecPostList;
    User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        toolbar = (Toolbar) findViewById(R.id.myPostsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Posts");
        setProperties();
    }

    /**
     * set the properties from the view
     */
    public void setProperties(){
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");

        postList = (PostList) getIntent().getSerializableExtra("postlist");


        this.add = findViewById(R.id.addButton);
        this.username = findViewById(R.id.username);
        this.userType = findViewById(R.id.user_type);
        this.img = findViewById(R.id.profile_image);
        recyclerView = findViewById(R.id.recycler_id);

        Picasso.get().load(currentUser.getAvatar()).into(img);
        adp = new MyPostsAdapter(MyPostsActivity.this, postList.getPostArray());
        recyclerView.setAdapter(adp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setElements();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });

    }

    /**
     * set the properties according to the currentUser
     */
    public void setElements()
    {
        username.setText(currentUser.getUsername());
        //Picasso.get().load(currentUser.getAvatar()).into(img);
        if(currentUser instanceof RegularUser)
            userType.setText("Regular User");
        else
            userType.setText("Admin User");
    }
    //for now

    @Override
    public void onBackPressed()
    {
        Intent pass = new Intent(MyPostsActivity.this, MainMenuActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    public void addPost()
    {
        Intent intent = new Intent(getBaseContext(), CreatePostActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("postlist", postList);
        intent.putExtra("fromPostList", false);
        startActivity(intent);
        finish();
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
