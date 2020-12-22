package com.example.bookurbook;
import com.example.bookurbook.ReportDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.example.bookurbook.models.WishList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements ReportPostDialogListener {
    //instance variables
    private Post post;
    private PostList postList;
    private User currentUser;
    private WishList wishlist;
    private FirebaseFirestore db;
    private boolean isPostListPreviousActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //variables
        Toolbar toolbar;
        TextView postTitleTextView;
        TextView postSellerTextView;
        TextView postUniversityTextView;
        TextView postCourseTextView;
        TextView postPriceTextView;
        TextView postDescriptionTextView;
        ImageButton reportButton;
        ImageButton wishlistButton;
        ImageButton chatButton;
        ImageButton homeButton;

        ImageView postPic;


        //method code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Post");
        chatButton = findViewById(R.id.chat_image_button);
        db = FirebaseFirestore.getInstance();

        post = (Post) getIntent().getSerializableExtra("post");
        if (getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin) getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser) getIntent().getSerializableExtra("currentUser");
        postList = (PostList) getIntent().getSerializableExtra("postlist");

        if(post.getOwner().getUsername().equals(currentUser.getUsername()))
            chatButton.setVisibility(View.GONE);

        if( (boolean) (getIntent().getExtras().get("fromPostList")))
        isPostListPreviousActivity = (boolean) getIntent().getExtras().get("fromPostList"); //does not get fromPostList intent?



        postPic = findViewById(R.id.postImageView);
        //initialization
        // post = new Post("This book is very nice :)", "MAT132 BOOK FOR CS STUDENTS", "Bilkent", "Math", 10, null, new RegularUser("Mehmet", "mehmet@ug.bilkent.edu.tr", null));
        postTitleTextView = findViewById(R.id.postTitleTextView);
        postSellerTextView = findViewById(R.id.postSellerTextView);
        postUniversityTextView = findViewById(R.id.postUniversityTextView);
        homeButton = findViewById(R.id.homeButton);
        postCourseTextView = findViewById(R.id.postCourseTextView);
        postPriceTextView = findViewById(R.id.postPriceTextView);
        postDescriptionTextView = findViewById(R.id.postDescriptionTextView);
        reportButton = findViewById(R.id.reportButton);
        wishlistButton = findViewById(R.id.wishlistButton);
        postTitleTextView.setText(post.getTitle());
        postSellerTextView.setText("Seller: " + post.getOwner().getUsername());
        postUniversityTextView.setText("University: " + post.getUniversity());
        postCourseTextView.setText("Course: " + post.getCourse());
        postPriceTextView.setText("Price: " + post.getPrice() + "");
        postDescriptionTextView.setText(post.getDescription());
        Picasso.get().load(post.getPicture()).into(postPic);
        System.out.println(post.getPicture() + "link");

        if (post.getOwner().getReports().size() >= 10)
            badRepAlert();


        /**
         * Will add the post to the wishlist if it is not included in the wishlist. If it is already
         * in the wishlist, it will remove it from the wishlist.
         */
        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wishlist.findPost(post)) {
                    wishlist.deletePost(post);
                    Toast.makeText(PostActivity.this, "You have deleted the item from the wishlist", Toast.LENGTH_LONG).show();
                } else {
                    wishlist.addPost(post);
                    Toast.makeText(PostActivity.this, "You have added the item to the wishlist", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * This button will open the report dialog and the user will be able to write their reports.
         */
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostReportDialog();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        Intent startIntent = new Intent(PostActivity.this, MainMenuActivity.class);
        startIntent.putExtra("currentUser" , currentUser);
        startActivity(startIntent);
        }

        });

        /**
         * This button starts a chat with the post owner
         */
        chatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.collection("chats").document(currentUser.getUsername() + ", " + post.getOwner().getUsername())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {
                                Intent pass = new Intent(PostActivity.this, ChatActivity.class);
                                Chat chat = new Chat(currentUser, post.getOwner(), currentUser.getUsername() + ", " + post.getOwner().getUsername());
                                pass.putExtra("currentUser", currentUser);
                                pass.putExtra("fromPostActivity", true);
                                pass.putExtra("post",post);
                                pass.putExtra("postlist", postList);
                                pass.putExtra("clickedChat", chat);
                                startActivity(pass);
                            }
                            else
                            {
                                db.collection("chats").document(post.getOwner().getUsername() + ", " + currentUser.getUsername())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                    {
                                        if ( document.exists())
                                        {
                                            Intent pass = new Intent(PostActivity.this, ChatActivity.class);
                                            Chat chat = new Chat(currentUser, post.getOwner(), post.getOwner().getUsername() + ", " + currentUser.getUsername());
                                            pass.putExtra("currentUser", currentUser);
                                            pass.putExtra("fromPostActivity", true);
                                            pass.putExtra("post",post);
                                            pass.putExtra("postlist", postList);
                                            pass.putExtra("clickedChat", chat);
                                            startActivity(pass);
                                        }
                                        else
                                        {
                                            Intent pass = new Intent(PostActivity.this, ChatActivity.class);
                                            Chat chat = new Chat(currentUser, post.getOwner(), post.getOwner().getUsername() + ", " + currentUser.getUsername());
                                            Map<String, Object> chatData = new HashMap<>();
                                            chatData.put("username1", post.getOwner().getUsername());
                                            chatData.put("username2", currentUser.getUsername());
                                            chatData.put("lastmessage", "");
                                            chatData.put("lastmessagedate", new Date());
                                            db.collection("chats").document(chat.getChatID()).set(chatData);
                                            pass.putExtra("currentUser", currentUser);
                                            pass.putExtra("clickedChat", chat);
                                            pass.putExtra("post",post);
                                            pass.putExtra("postlist", postList);
                                            pass.putExtra("fromPostActivity", true);
                                            startActivity(pass);
                                        }

                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast chatError = Toast.makeText(PostActivity.this,"Something is wrong. Please check your Internet connection.", Toast.LENGTH_LONG);
                        }
                    }
                });
            }

        });
    }


    /**
     * This method is created in order to create a pop up dialog using ReportDialog class.
     */
    public void openPostReportDialog()
    {
        ReportDialog dialog = new ReportDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void applyTexts(String description, String category) {
        post.report(description, category);
        post.setReportNum(post.getReportNum()+1);
        //System.out.println(post.getReports().get(0).getDescription());
        //System.out.println(post.getReports().get(0).getCategory());
    }

    /**
     * This method creates a pop up dialog before entering the screen if the seller of the post
     * has been reported several times.
     */
    public void badRepAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
        builder.setTitle("Attention");
        builder.setMessage("This user has been reported several times. Be careful with the user or the post.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onBackPressed() {
        Intent pass;
        if(isPostListPreviousActivity)
        pass = new Intent(PostActivity.this, PostListActivity.class);
        else
            pass = new Intent(PostActivity.this, MyPostsActivity.class);
        pass.putExtra("postlist", postList);
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

