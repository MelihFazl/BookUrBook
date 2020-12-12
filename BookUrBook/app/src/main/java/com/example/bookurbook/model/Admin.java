package com.example.bookurbook.model;
import android.widget.ImageView;

public class Admin extends User
{
    //instance variables
    public Admin(String username, String email, ImageView avatar)
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

