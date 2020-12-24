package com.example.bookurbook.models;

import java.util.Comparator;

public class PostTitleComparator implements Comparator<Post>
{
    @Override
    public int compare(Post o1, Post o2)
    {
        if (o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()) > 0)
            return 1;
        else if (o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()) == 0 )
            return 0;
        else
            return -1;
    }
}

