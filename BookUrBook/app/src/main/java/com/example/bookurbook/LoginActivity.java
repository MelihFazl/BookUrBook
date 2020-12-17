package com.example.bookurbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    private EditText email;
    private EditText password;
    private TextView passwordForget;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private User currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void init()
    {
        login = findViewById(R.id.loginbutton);
        register = findViewById(R.id.registerbutton);
        email = findViewById(R.id.editemail);
        password = findViewById(R.id.editpassword);
        passwordForget = findViewById(R.id.forgotmypass);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        login.setOnClickListener(new View.OnClickListener()
        {
           public void onClick(View v)
           {
               loginUser();
           }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(pass);
            }
        });
        passwordForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgottenPasswordActivity.class));
            }
        });
    }

    private void loginUser()
    {
        if(TextUtils.isEmpty(email.getText().toString()))
        {
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password.getText().toString()))
        {
            Toast.makeText(this,"Password field is empty", Toast.LENGTH_LONG).show();

        }
        else if(!email.getText().toString().contains("@"))
        {
            Toast.makeText(this, "Wrong email",  Toast.LENGTH_LONG).show();

        }
        else if(!email.getText().toString().substring(email.getText().toString().indexOf("@"), email.getText().toString().length()).contains(".edu.tr"))
        {
           Toast.makeText(this, "This is not a edu.tr mail",  Toast.LENGTH_LONG).show();
        }
        else {
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Login successful",  Toast.LENGTH_LONG).show();
                        db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.getBoolean("admin"))
                                    currentUser = new Admin(documentSnapshot.getString("username"), documentSnapshot.getString("email"), null);
                                else
                                    currentUser = new RegularUser(documentSnapshot.getString("username"), documentSnapshot.getString("email"), null);
                                storage.getReference().child("images/profile_pictures/" + auth.getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        currentUser.setAvatar(uri.toString());
                                        Intent pass = new Intent(LoginActivity.this, MainMenuActivity.class);
                                        pass.putExtra("user", currentUser);
                                        startActivity(pass);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        storage.getReference().child("images/profile_pictures/default.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                currentUser.setAvatar(uri.toString());
                                                Intent pass = new Intent(LoginActivity.this, MainMenuActivity.class);
                                                pass.putExtra("user", currentUser);
                                                startActivity(pass);
                                                finish();
                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "OOPS! Login is not successful!",  Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() { }
}