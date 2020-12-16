package com.example.bookurbook.models;
import java.io.*;
import java.util.*;

public class UserSpecPostList extends PostList implements Serializable
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