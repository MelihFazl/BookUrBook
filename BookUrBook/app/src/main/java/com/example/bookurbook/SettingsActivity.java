package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.SendNotificationPack.Token;
import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {


    private Button logout;
    private Button select;
    private Button blocklist;
    private TextView userDetails;
    private ImageView profilePic;
    private Uri imageUri;
    private User currentUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private Intent pass;
    private Toolbar toolbar;

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
        blocklist = findViewById(R.id.blocked_users);
        profilePic = findViewById(R.id.profilepic);
        userDetails = findViewById(R.id.userdetails);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Settings");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");
        System.out.println("In Settings Current User Email: " + currentUser.getEmail());
        System.out.println("Current User Avatar: " + currentUser.getAvatar());
        Picasso.get().load(currentUser.getAvatar()).into(profilePic);
        if(currentUser instanceof Admin)
            userDetails.setText(currentUser.getUsername()+ "\n" + "Admin User");
        else
            userDetails.setText(currentUser.getUsername());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("tokens").document(auth.getUid()).set(new Token(""));
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
        blocklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setBlockedUsers(new ArrayList<User>());
            db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                List<String> blockedUsernames = Collections.emptyList();
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        blockedUsernames = (List<String> )documentSnapshot.get("blockedusers");
                        if(blockedUsernames.size() == 0)
                        {
                            Intent pass = new Intent(SettingsActivity.this, MyBlockListActivity.class);
                            pass.putExtra("currentUser", currentUser);
                            startActivity(pass);
                            finish();
                        }
                        for(int i = 0; blockedUsernames.size() > i; i++)
                        {
                            db.collection("users").whereEqualTo("username", blockedUsernames.get(i)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for(DocumentSnapshot doc : task.getResult())
                                    {   User toBeAdded;
                                        if(doc.getBoolean("admin"))
                                            toBeAdded = new Admin(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));
                                        else
                                            toBeAdded = new RegularUser(doc.getString("username"), doc.getString("email"), doc.getString("avatar"));
                                        if(!currentUser.getBlockedUsers().contains(toBeAdded))
                                           currentUser.blockUser(toBeAdded);
                                    }
                                    Intent pass = new Intent(SettingsActivity.this, MyBlockListActivity.class);
                                    pass.putExtra("currentUser", currentUser);
                                    startActivity(pass);
                                    finish();

                                }
                            });
                        }
                    }
                });
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
                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
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
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}