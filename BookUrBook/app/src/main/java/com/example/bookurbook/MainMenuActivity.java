package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class MainMenuActivity extends AppCompatActivity {

    private View topleft;
    private View topright;
    private View botleft;
    private View botright;
    private ImageView wishlist;
    private User currentUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private PostList postList;
    private User currentPostOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if(getIntent().getSerializableExtra("user") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("user");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("user");
        System.out.println("Main Menu Current User email:  " + currentUser.getEmail());
    }


    public void init()
    {
        topleft = findViewById(R.id.topleft);
        topright = findViewById(R.id.topright);
        botleft = findViewById(R.id.botleft);
        botright = findViewById(R.id.botright);
        wishlist = findViewById(R.id.wishlist);



        topleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postList = new PostList();
                Intent pass = new Intent(MainMenuActivity.this, PostListActivity.class);
                db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (DocumentSnapshot document : task.getResult())
                            {
                                db.collection("users").whereEqualTo("username", document.getString("username")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        FirebaseFirestore db;
                                        for(DocumentSnapshot doc: task.getResult())
                                        {
                                            currentPostOwner = new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));
                                        }
                                        postList.addPost(new Post(document.getString("description"), document.getString("title"), document.getString("university")
                                                , document.getString("course"), document.getLong("price").intValue(), document.getString("picture"), currentPostOwner));
                                        Intent pass = new Intent(MainMenuActivity.this, PostListActivity.class);
                                        pass.putExtra("user", currentUser);
                                        pass.putExtra("postlist", postList);
                                        startActivity(pass);
                                    }
                                });
                            }
                        }
                    }
                });

            }
        });
        topright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, MyChatsActivity.class);
                pass.putExtra("user", currentUser);
                startActivity(pass);
            }
        });

        botleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MyPostsActivity.class));
            }
        });
        botright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, SettingsActivity.class);
                pass.putExtra("user", currentUser);
                startActivity(pass);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, WishlistActivity.class));
            }
        });

    }
}