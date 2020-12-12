package com.example.ModelClasses;

import java.io.*;
import java.util.*;

public class WishList {

    private ArrayList<Post> posts;
    private User user;

    public WishList(User user) {
        this.user = user;
        posts = new ArrayList<Post>();
    }

    public void addPost(Post post) {
        posts.add(post);
        post.addUser(user);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public void sendNotification(Post post) {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

}
