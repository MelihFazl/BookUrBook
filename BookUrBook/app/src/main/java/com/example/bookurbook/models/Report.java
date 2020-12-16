package com.example.bookurbook.models;
import java.io.*;
import java.util.*;

public class Report implements Serializable
{
    //instance variables
    private String description;
    private User owner;
    private String category;

    //constructor
    public Report(String description, String category, User owner)
    {
        this.description = description;
        this.owner = owner;
        this.category = category;
    }

}