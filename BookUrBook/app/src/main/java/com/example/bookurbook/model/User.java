package com.example.bookurbook.model;


import android.media.Image;
import java.awt.*;
import java.io.*;
import java.util.*;

public abstract class User implements Reportable
{
    //instance variables
    private String username;
    private String email;
    private boolean banned;
    private ArrayList<Report> reports;
    //!!!!private FirebaseAuth auth;!!!!!!!!!!!!!!!!!!!
    //!!!!private FirebaseFirestore database;!!!!!!!!!
    private Image avatar;
    private UserSpecPostList userPostList;
    private UserList blockedUsers;

    //constructor
    public User(String username, String email, Image avatar)///PASSWORD AS PARAMETER-DATABASE??
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

    public Image getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Image avatar)
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
}
