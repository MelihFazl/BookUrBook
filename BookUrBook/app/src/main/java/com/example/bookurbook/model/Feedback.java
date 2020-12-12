package com.example.bookurbook.model;

public class Feedback
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
