package com.example.bookurbook.models;
import android.provider.ContactsContract;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.*;
import java.util.*;

public abstract class User implements Reportable
{
    //instance variables
    private String username;
    private String email;
    private boolean banned;
    private ArrayList<Report> reports;
    private ImageView avatar;
    private UserSpecPostList userPostList;
    private UserList blockedUsers;
    private FirebaseAuth auth;
    private DatabaseReference db;


    //constructor
    public User(String username, String email, ImageView avatar)///PASSWORD AS PARAMETER-DATABASE?? //yes of course!.
    {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        banned = false;
        reports = new ArrayList<>();
        userPostList = new UserSpecPostList(this);
        blockedUsers = new UserList();
    }
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public boolean isBanned()
    {
        return banned;
    }

    public void setBanned(boolean banned)
    {
        this.banned = banned;
    }

    public ArrayList<Report> getReports()
    {
        return reports;
    }

    public void setReports(ArrayList<Report> reports)
    {
        this.reports = reports;
    }

    public ImageView getAvatar()
    {
        return avatar;
    }

    public void setAvatar(ImageView avatar)
    {
        this.avatar = avatar;
    }

    public UserSpecPostList getUserPostList()
    {
        return userPostList;
    }

    public void setUserPostList(UserSpecPostList userPostList)
    {
        this.userPostList = userPostList;
    }

    public UserList getBlockedUsers()
    {
        return blockedUsers;
    }

    public void setBlockedUsers(UserList blockedUsers)
    {
        this.blockedUsers = blockedUsers;
    }

    /**public FirebaseAuth getAuth()
     {
     return auth;
     }

     public void setAuth(FirebaseAuth auth)
     {
     this.auth = auth;
     }

     public FirebaseFirestore getDatabase()
     {
     return database;
     }


     public void setDatabase(FirebaseFirestore database)
     {
     this.database = database;
     }
     */

    /**
     public void changePassword()
     {
     throw new UnsupportedOperationException("The method is not implemented yet.");
     }
     */

    public void blockUser(User user)
    {
            blockedUsers.addUser(user);
    }

    @Override
    public void report(String description, String category)
    {
        Report report = new Report(description, category, this);
        reports.add(report);
    }

    /**
     * This method will get called whenever an Admin bans a user. The method will set take the necessary
     * actions such as deleting posts from PostList... in order to prevent the banned user from accessing the app.
     */
    public void updateBannedUser()
    {
    }

    private class RegisterCompleteListener<AuthResult> implements OnCompleteListener
    {
        @Override
        public void onComplete(@NonNull Task task)
        {

        }
    }





}



