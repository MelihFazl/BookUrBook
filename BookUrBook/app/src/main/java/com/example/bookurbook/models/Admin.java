package com.example.bookurbook.models;
import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;

public class Admin extends User implements Serializable
{
    //instance variables
    public Admin(String username, String email, String  avatar)
    {
        super(username, email, avatar);
    }

    public void ban(User other)
    {
        other.setBanned(true);
    }

    public void deletePost(Post post, PostList usedList)
    {
        usedList.deletePost(post);
    }

}

