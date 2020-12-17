package com.example.bookurbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.RegularUser;

public class PostActivity extends AppCompatActivity implements ReportPostDialogListener {
    //instance variables
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        Toolbar toolbar;
        TextView postTitleTextView;
        TextView postSellerTextView;
        TextView postUniversityTextView;
        TextView postCourseTextView;
        TextView postPriceTextView;
        TextView postDescriptionTextView;
        ImageButton reportButton;

        //method code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
         post = (Post) getIntent().getSerializableExtra("post");
        }
        post = new Post("This book is very nice :)", "MAT132 BOOK FOR CS STUDENTS", "Bilkent", "Math", 10, null, new RegularUser("Mehmet", "mehmet@ug.bilkent.edu.tr", null));
        postTitleTextView = findViewById(R.id.postTitleTextView);
        postSellerTextView = findViewById(R.id.postSellerTextView);
        postUniversityTextView = findViewById(R.id.postUniversityTextView);
        postCourseTextView = findViewById(R.id.postCourseTextView);
        postPriceTextView = findViewById(R.id.postPriceTextView);
        postDescriptionTextView = findViewById(R.id.postDescriptionTextView);
        reportButton = findViewById(R.id.reportButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostReportDialog();
            }
        });

        /**ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
         homeButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        Intent startIntent = new Intent(getApplicationContext(), HomeScreen.class);
        startIntent.putExtra("com.example.quicklauncher.SOMETHING" , "I am trying something!");
        startActivity(startIntent);
        }
        });*/

        postTitleTextView.setText(post.getTitle());
        postSellerTextView.setText("Seller: " + post.getOwner().getUsername());
        postUniversityTextView.setText("University: " + post.getUniversity());
        postCourseTextView.setText("Course: " + post.getCourse());
        postPriceTextView.setText("Price: " + post.getPrice() + "");
        postDescriptionTextView.setText(post.getDescription());
        ///IMAGE WILL BE ADDED!!!!!!!!!!!!

    }

    public void openPostReportDialog() {
        ReportDialog dialog = new ReportDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void applyTexts(String description, String category) {
        post.report(description, category);
        //System.out.println(post.getReports().get(0).getDescription());
        //System.out.println(post.getReports().get(0).getCategory());
    }
}