package com.example.bookurbook.models;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.*;

public class UserList implements Serializable
{
    //instance variables
    private ArrayList<User> userArray;
    //!!!!!!!!private FirebaseFirestore database;
    //!!!!!!!!!private FirebaseAuth auth;

    //constructors
    public UserList()
    {
        userArray = new ArrayList<>();
    }

    public void createUser(String username, String email, String password, String avatar, boolean isAdmin)//!!!!PASSWORD AS PARAMETER - DATABASE?
    {
        if(isAdmin)
        {
            Admin admin = new Admin(username, email, avatar);
            addUser(admin);
        }
        else
        {
            RegularUser regUser = new RegularUser(username, email, avatar);
            addUser(regUser);
        }
    }

    public User findUserByUsername(String username)
    {
        for(User user: userArray)
        {
            if(user.getUsername().equals(username));
            return user;
        }
        return null;
    }

    public void addUser(User user)
    {
        userArray.add(user);
    }

    public void deleteUser(User user)
    {
        for(int i = 0; i < userArray.size(); i++)
        {
            if(user.getEmail().equals(userArray.get(i).getEmail()));
            userArray.remove(i);
        }
    }


}