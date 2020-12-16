package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.UserSpecPostList;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView img;
    TextView username, userType;
    MyPostsAdapter adp;
    PostList postList;
    RegularUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        setProperties();
    }

    /**
     * set the properties from the view
     */
    public void setProperties(){
        this.username = findViewById(R.id.username);
        this.userType = findViewById(R.id.user_type);
        this.img = findViewById(R.id.profile_image);
        recyclerView = findViewById(R.id.recycler_id);
        add();
        adp = new MyPostsAdapter(getBaseContext(), postList.getPostArray());
        recyclerView.setAdapter(adp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setElements();


    }

    /**
     * set the properties according to the user
     */
    public void setElements()
    {
        username.setText(user.getUsername());
        //Ferhat will fix img.setImageResource(user.getAvatar());
        if(user instanceof RegularUser)
            userType.setText("Regular User");
        else
            userType.setText("Admin User");
    }
    //for now
    public void add()    // later change this method to add posts from database ???
    {
        //user = new RegularUser("miray","miray.ayerdem@ug.bilkent.edu.tr",R.drawable.userimg);
        //postList = new UserSpecPostList(user) ;
        //postList.addPost(new Post("b","BigJava Objects", "bilkent", "cs", 100, R.drawable.bru4, user));
        //postList.addPost(new Post("b","BigJava Objects", "ottü", "cs", 100, R.drawable.bru4,user));
        //postList.addPost(new Post("j","Calculus", "ankara", "100", 80,  R.drawable.bru, user));
        //postList.addPost(new Post("aa","discrete math", "boğaziçi", "10", 1000,  R.drawable.bru, user));
        //postList.addPost(new Post("ı","humanity", "itü", "1000", 200,  R.drawable.bru,user));

    }

    /**
     * pass to Create Post Activity
     * @param view view to be listened
     */
    /**public void addPost(View view){
     Intent intent = new Intent(getBaseContext(), CreatePost.class);
     intent.putExtra("user", user);
     startActivity(intent);

     }/*
     public void back(View view){
     /**EKLENECEK*/
    //}

}