package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    private ImageButton adminPanelButton;
    private TextView adminPanelTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if (getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin) getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser) getIntent().getSerializableExtra("currentUser");
        toolbar = findViewById(R.id.mainMenuToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Menu");
        init();
        db = FirebaseFirestore.getInstance();//initin üstünde olabilir hatırlayamadım
        auth = FirebaseAuth.getInstance();//initin üstünde olabilir hatırlayamadım
        System.out.println("Main Menu Current User email:  " + currentUser.getEmail());
    }


    public void init() {
        topleft = findViewById(R.id.topleft);
        topright = findViewById(R.id.topright);
        botleft = findViewById(R.id.botleft);
        botright = findViewById(R.id.botright);
        wishlist = findViewById(R.id.wishlist);
        adminPanelButton = findViewById(R.id.adminPanelButton);
        adminPanelTextView = findViewById(R.id.adminPanelTextView);

        if (currentUser instanceof Admin) {
            adminPanelButton.setVisibility(View.VISIBLE);
            adminPanelTextView.setVisibility(View.VISIBLE);
        }

        topleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postList = new PostList();
                Intent pass = new Intent(MainMenuActivity.this, PostListActivity.class);
                db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> blockedUsernames = (List<String>) documentSnapshot.get("blockedusers");
                        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        db.collection("users").whereEqualTo("username", document.getString("username")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (DocumentSnapshot doc : task.getResult()) {
                                                    currentPostOwner = new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));
                                                    currentPostOwner.setBanned(doc.getBoolean("banned"));
                                                }
                                                if (!document.getBoolean("sold")) {
                                                    if (!blockedUsernames.contains(currentPostOwner.getUsername()) && !currentPostOwner.isBanned()) {
                                                        postList.addPost(new Post(document.getString("description"), document.getString("title"), document.getString("university")
                                                                , document.getString("course"), document.getLong("price").intValue(), document.getString("picture"), currentPostOwner, (String) document.get("id")));
                                                        postList.getPostArray().get(postList.getPostArray().size() - 1).setReportNum(document.getLong("reports").intValue());
                                                    }
                                                }
                                                pass.putExtra("currentUser", currentUser);
                                                pass.putExtra("postlist", postList);
                                                startActivity(pass);
                                                finish();
                                            }

                                        });

                                    }
                                }
                                pass.putExtra("currentUser", currentUser);
                                pass.putExtra("postlist", postList);
                                startActivity(pass);
                            }
                        });

                    }

                });
            }
        });

        topright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, MyChatsActivity.class);
                db.collection("users").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        List<String> blockedUsernamesList = (List<String>) documentSnapshot.get("blockedusers");
                        ArrayList<String> blockedUsernames = new ArrayList<String>();
                        blockedUsernames.addAll(blockedUsernamesList);
                        pass.putExtra("blockedUsernames", blockedUsernames);
                        pass.putExtra("currentUser", currentUser);
                        startActivity(pass);
                    }
                });
            }
        });

        botleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postList = new PostList();
                Intent pass = new Intent(MainMenuActivity.this, MyPostsActivity.class);
                db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                db.collection("users").whereEqualTo("username", document.getString("username")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        FirebaseFirestore db;
                                        for (DocumentSnapshot doc : task.getResult()) {
                                            currentPostOwner = new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));

                                        }
                                        if (document.getString("username").equals(currentUser.getUsername())) {
                                            postList.addPost(new Post(document.getString("description"), document.getString("title"), document.getString("university")
                                                    , document.getString("course"), document.getLong("price").intValue(), document.getString("picture"), currentPostOwner, (String) document.get("id")));
                                            postList.getPostArray().get(postList.getPostArray().size() - 1).setSold(document.getBoolean("sold"));
                                        }
                                        pass.putExtra("currentUser", currentUser);
                                        pass.putExtra("postlist", postList);
                                        startActivity(pass);
                                        finish();
                                    }

                                });

                            }
                        }
                        pass.putExtra("currentUser", currentUser);
                        pass.putExtra("postlist", postList);
                        startActivity(pass);

                    }
                });

            }
        });
        botright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(MainMenuActivity.this, SettingsActivity.class);
                pass.putExtra("currentUser", currentUser);
                startActivity(pass);
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postList = new PostList();
                Intent pass = new Intent(MainMenuActivity.this, WishlistActivity.class);
                db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        db.collection("users").whereEqualTo("username", document.getString("username")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                List<String> wished = (List<String>) documentSnapshot.get("wishlist");
                                                for (DocumentSnapshot doc : task.getResult()) {
                                                    currentPostOwner = new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));
                                                    currentPostOwner.setBanned(doc.getBoolean("banned"));
                                                }
                                                if (!document.getBoolean("sold")) {
                                                    if (wished.contains(document.getString("id")) && !currentPostOwner.isBanned())
                                                    {
                                                        postList.addPost(new Post(document.getString("description"), document.getString("title"), document.getString("university")
                                                                , document.getString("course"), document.getLong("price").intValue(), document.getString("picture"), currentPostOwner, (String) document.get("id")));
                                                    }
                                                }
                                                currentUser.setWishList(postList.getPostArray());
                                                pass.putExtra("currentUser", currentUser);
                                                startActivity(pass);
                                                finish();
                                            }

                                        });

                                    }
                                } else {
                                    pass.putExtra("currentUser", currentUser);

                                }
                            }
                        });

                    }

                });
            }
        });

        adminPanelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<User> reportedUsers = new ArrayList<User>();
                Intent adminPanel = new Intent(MainMenuActivity.this, AdminPanelActivity.class);
                adminPanel.putExtra("currentUser", currentUser);
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot doc : task.getResult())
                        {
                            if(doc.getLong("reports").intValue() > 0)
                            {
                                if(doc.getBoolean("admin"))
                                    reportedUsers.add(new Admin(doc.getString("username"), doc.getString("email"), doc.getString("avatar")));
                                else
                                    reportedUsers.add(new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar")));
                                reportedUsers.get(reportedUsers.size() - 1).setReportNum(doc.getLong("reports").intValue());
                                reportedUsers.get(reportedUsers.size() -1).setBanned(doc.getBoolean("banned"));
                            }
                        }
                        adminPanel.putExtra("userlist", reportedUsers);
                        startActivity(adminPanel);
                        finish();
                    }
                });
                adminPanel.putExtra("userlist", reportedUsers);
                startActivity(adminPanel);
                finish();
            }
        });

    }
}