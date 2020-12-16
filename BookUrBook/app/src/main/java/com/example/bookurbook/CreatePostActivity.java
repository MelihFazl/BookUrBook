package com.example.bookurbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;

public class CreatePostActivity extends AppCompatActivity {
    //instance variables
    private Post post;
    private PostList list;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        Toolbar toolbar;
        EditText postTitleCreatePost;
        Spinner spinner;
        Spinner spinner2;
        EditText postPrice;
        EditText postDescriptionCreatePost;
        ImageButton homeButton;
        ImageButton applyButton;
        ImageButton photoUpload;

        //method code
        /**Bundle extras = getIntent().getExtras();
         if (extras != null) {
         post = (Post) getIntent().getSerializableExtra("MyClass");
         }*/
        user = new RegularUser("Mehmet", "mehmet@ug.bilkent.edu.tr", null);
        list = new PostList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        toolbar = findViewById(R.id.toolbar_without_report);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Create Post");

        postTitleCreatePost = findViewById(R.id.postTitleCreatePost);
        spinner = findViewById(R.id.createPostSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreatePostActivity.this, R.array.Universities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2 = findViewById(R.id.createPostSpinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(CreatePostActivity.this, R.array.Courses, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        photoUpload = findViewById(R.id.photoUpload);

        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postPrice = findViewById(R.id.postPriceCreatePost);
        postDescriptionCreatePost = findViewById(R.id.postDescriptionCreatePost);
        homeButton = findViewById(R.id.homeButtonCreatePost);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        applyButton = findViewById(R.id.applyButtonCreatePost);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure that you want to create the Post?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if(postTitleCreatePost.getText().toString().equals("")) {
                            Toast.makeText(CreatePostActivity.this, "You need to enter a title!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else if (postPrice.getText().toString().equals("")){
                            Toast.makeText(CreatePostActivity.this, "You need to enter the price!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else {
                            post = new Post(postDescriptionCreatePost.getText().toString(), postTitleCreatePost.getText().toString()
                                    , spinner.getSelectedItem().toString(), spinner2.getSelectedItem().toString(), Double.parseDouble(postPrice.getText().toString())
                                    , null, user);
                            list.addPost(post);
                            Toast.makeText(CreatePostActivity.this, "You have successfully created the post!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}
