package com.example.bookurbook.models;
import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.*;

public abstract class User implements Reportable, Serializable
{
    //instance variables
    private String username;
    private String email;
    private boolean banned;
    private ArrayList<Report> reports;
    private int reportNum;
    private String avatar;
    private UserSpecPostList userPostList;
    private ArrayList<User> blockedUsers;

    //constructor
    public User(String username, String email, String avatar)///PASSWORD AS PARAMETER-DATABASE?? //yes of course!.
    {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        reports = new ArrayList<Report>();
        userPostList = new UserSpecPostList(this);
        blockedUsers = new ArrayList<User>();
        reportNum = 0;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }

    public String getEmail()
    {
        return email;
    }

    //public UserDatabaseConnection getUdb() {
      //  return udb;
    //}

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

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
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

    public ArrayList<User> getBlockedUsers()
    {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers)
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
            blockedUsers.add(user);
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







}



