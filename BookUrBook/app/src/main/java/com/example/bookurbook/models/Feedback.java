package com.example.bookurbook.models;

import java.io.Serializable;

public class Feedback implements Serializable
{
    //instance variables
    private User owner;
    private String description;

    //constructor
    public Feedback(User owner, String description)
    {
        this.owner = owner;
        this.description = description;
    }

}
