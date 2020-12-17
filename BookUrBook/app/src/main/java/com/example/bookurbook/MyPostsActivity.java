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
import com.squareup.picasso.Picasso;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView img;
    TextView username, userType;
    MyPostsAdapter adp;
    PostList postList;
    ImageView temp;
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
        Picasso.get().load(user.getAvatar()).into(img);
        adp = new MyPostsAdapter(MyPostsActivity.this, postList.getPostArray());
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
        //Picasso.get().load(user.getAvatar()).into(img);
        if(user instanceof RegularUser)
            userType.setText("Regular User");
        else
            userType.setText("Admin User");
    }
    //for now
    public void add()    // later change this method to add posts from database ???
    {
        user = new RegularUser("mirayayerdem","miray.ayerdem@ug.bilkent.edu.tr","https://firebasestorage.googleapis.com/v0/b/bookurbook-a02e4.appspot.com/o/images%2Fprofile_pictures%2F8zD7ahsNJUhACYe9YZlSAgxOqmr2?alt=media&token=2fc17f75-c28c-43de-b4a5-10159b199bf0");
        postList = new UserSpecPostList(user) ;
        postList.addPost(new Post("b","BigJava Late Objects", "bilkent", "cs", 100, img, user));
        postList.addPost(new Post("b","Humanity GILGAMIŞ", "ottü", "cs", 30, img,user));
        postList.addPost(new Post("j","Calculus", "ankara", "100", 80, img, user));
        postList.addPost(new Post("aa","discrete math", "boğaziçi", "10", 1000, img, user));
        postList.addPost(new Post("ı","BigJava Late Objects", "itü", "1000", 200,  img,user));

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