package com.example.bookurbook.models;

import java.io.*;
import java.util.*;

public class WishList implements Serializable{

    private ArrayList<Post> posts;
    private User user;

    public WishList(User user)
    {
        this.user = user;
        posts = user.getWishList();
    }

    public void addPost(Post post)
    {
        posts.add(post);
    }

    public void deletePost(Post post)
    {
        posts.remove(post);

    }

    public void sendNotification(Post post) {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public boolean findPost(Post post)
    {
        for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext(); )
        {
            Post listPost = iterator.next();
            if (listPost == post) {
                return true;
            }

        }
        return false;
    }

}
