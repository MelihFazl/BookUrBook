package com.example.bookurbook.model;
import java.io.*;
import java.util.*;

public class UserSpecPostList extends PostList
{
    //instance variables
    private User owner;

    //constructor
    public UserSpecPostList(User owner)
    {
        this.owner = owner;
    }

    public void updateUserSpecPostList(PostList postList)
    {
        setPostArray(postList.filterByOwner(owner));
    }

}