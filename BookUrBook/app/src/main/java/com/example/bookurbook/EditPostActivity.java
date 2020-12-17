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

public class EditPostActivity extends AppCompatActivity {
    //instance variables
    private Post post;
    private PostList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        Toolbar toolbar;
        EditText postTitleEditText;
        Spinner spinner;
        Spinner spinner2;
        EditText postPrice;
        EditText postDescriptionEditText;
        ImageButton homeButton;
        ImageButton deleteButton;
        ImageButton applyButton;
        ImageButton photoUpload;

        //method code
        /**Bundle extras = getIntent().getExtras();
         if (extras != null) {
         post = (Post) getIntent().getSerializableExtra("MyClass");
         }*/
        post = new Post("This book is very nice :)", "MAT132 BOOK FOR CS STUDENTS", "Gazi", "EEE", 10, null, new RegularUser("Mehmet", "mehmet@ug.bilkent.edu.tr", null));
        list = new PostList();
        list.addPost(post);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        toolbar = findViewById(R.id.toolbar_with_trashcan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Post");

        postTitleEditText = findViewById(R.id.postTitleEditText);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditPostActivity.this, R.array.Universities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        for(int i = 0; i < spinner.getCount(); i++) {
            if ( spinner.getItemAtPosition(i).toString().equals(post.getUniversity()))
                spinner.setSelection(i);
        }

        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(EditPostActivity.this, R.array.Courses, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        for(int i = 0; i < spinner2.getCount(); i++) {
            if ( spinner2.getItemAtPosition(i).toString().equals(post.getCourse()))
                spinner2.setSelection(i);
        }

        postPrice = findViewById(R.id.postPriceEditText);
        postDescriptionEditText = findViewById(R.id.postDescriptionEditText);
        postTitleEditText.setText(post.getTitle());
        postPrice.setText(post.getPrice() + "");
        postDescriptionEditText.setText(post.getDescription());
        photoUpload = findViewById(R.id.photoUpload2);
        ////////// WILL GET POST IMAGE !!!!!photoUpload.setImage


        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPostActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure that you want to delete the Post?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        list.deletePost(post);
                        dialog.dismiss();
                        Toast.makeText(EditPostActivity.this, "You have successfully deleted your Post!", Toast.LENGTH_LONG).show();
                        //Then it will close the screen automatically!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
        applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPostActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure that you want to apply the changes?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(postTitleEditText.getText().toString().equals("")) {
                            Toast.makeText(EditPostActivity.this, "You need to enter a title!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else if (postPrice.getText().toString().equals("")){
                            Toast.makeText(EditPostActivity.this, "You need to enter the price!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else {
                            post.setDescription(postDescriptionEditText.getText().toString());
                            post.setTitle(postTitleEditText.getText().toString());
                            post.setUniversity(spinner.getSelectedItem().toString());
                            post.setCourse(spinner2.getSelectedItem().toString());
                            post.setPrice(Integer.parseInt(postPrice.getText().toString()));
                            Toast.makeText(EditPostActivity.this, "You have successfully applied your changes!", Toast.LENGTH_LONG).show();
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