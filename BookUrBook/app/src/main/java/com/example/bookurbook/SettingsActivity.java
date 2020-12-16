package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {


    private Button logout;
    private Button select;
    private TextView userDetails;
    private ImageView profilePic;
    private Uri imageUri;
    private User currentUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private Intent pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }
    public void init()
    {
        logout = findViewById(R.id.logout);
        select = findViewById(R.id.selectImage);
        profilePic = findViewById(R.id.profilepic);
        userDetails = findViewById(R.id.userdetails);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if(getIntent().getSerializableExtra("user") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("user");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("user");
        System.out.println("SETTINGSDEYIZ ABI " + currentUser.getEmail());
        System.out.println("avatarimiz :aSDSADA " + currentUser.getAvatar());
        Picasso.get().load(currentUser.getAvatar()).into(profilePic);
        if(currentUser instanceof Admin)
            userDetails.setText(currentUser.getUsername()+ "\n" + "Admin User");
        else
            userDetails.setText(currentUser.getUsername());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent (SettingsActivity.this, LoginActivity.class));
                finish();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }

    private void choosePicture()
    {
        Intent galleryOpen = new Intent();
        galleryOpen.setType("image/*");
        galleryOpen.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryOpen, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            uploadPic();
        }


    }

    private void uploadPic()
    {
        StorageReference picRef = storageReference.child("images/profile_pictures/" + auth.getCurrentUser().getUid());
        picRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Toast.makeText(getApplicationContext(), "BRUH", Toast.LENGTH_LONG).show();
                        picRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(profilePic);
                                HashMap<String, Object> newData = new HashMap();
                                newData.put("avatar", uri.toString());
                                currentUser.setAvatar(uri.toString());
                                db.collection("users").document(auth.getCurrentUser().getUid()).set(newData, SetOptions.merge());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        pass = new Intent(SettingsActivity.this, MainMenuActivity.class);
        pass.putExtra("user", currentUser);
        startActivity(pass);
    }
}