package com.example.bookurbook.model;

import android.media.Image;

public class Admin extends User
{
    //instance variables
    public Admin(String username, String email, Image avatar)
    {
        super(username, email, avatar);
    }

    public void ban(User other)
    {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public void deletePost(Post post)
    {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

}

