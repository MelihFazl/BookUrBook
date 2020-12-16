package com.example.bookurbook.models;
import java.io.*;
import java.util.*;
import android.widget.ImageView;

public class Post implements Reportable, Serializable
{
    //instance variables
    private String description;
    private String title;
    private String university;
    private String course;
    private double price;
    private ImageView picture;
    private User owner;
    private boolean sold;
    private ArrayList<Report> reports;

    //constructors
    public Post(String description, String title, String university, String course, double price, ImageView picture, User owner)
    {
        this.description = description;
        this.title = title;
        this.university = university;
        this.course = course;
        this.price = price;
        this.picture = picture;
        this.owner = owner;
        sold = false;
        reports = new ArrayList<>();
    }

    //getters
    public String getDescription()
    {
        return description;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUniversity()
    {
        return university;
    }

    public String getCourse()
    {
        return course;
    }

    public double getPrice()
    {
        return price;
    }

    public ImageView getPicture()
    {
        return picture;
    }

    public User getOwner()
    {
        return owner;
    }

    public boolean isSold()
    {
        return sold;
    }

    public ArrayList<Report> getReports()
    {
        return reports;
    }

    //setters
    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setUniversity(String university)
    {
        this.university = university;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setPicture(ImageView picture)
    {
        this.picture = picture;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public void setReports(ArrayList<Report> reports)
    {
        this.reports = reports;
    }

    //other methods
    /**
     * This method is put in order to edit wanted post variables
     * @param description description
     * @param title title
     * @param university Univeristy
     * @param course course
     * @param price price
     * @param picture picture
     */
    public void editPost(String description, String title, String university, String course, double price, ImageView picture)
    {
        if (!description.replaceAll("\\s+","").equals(""))
            this.description = description;
        if (!title.replaceAll("\\s+","").equals(""))
            this.description = description;
        if (!university.replaceAll("\\s+","").equals(""))
            this.description = description;
        if (!course.replaceAll("\\s+","").equals(""))
            this.description = description;
        this.price = price;
        this.picture = picture;
    }

    @Override
    public void report(String description, String category)
    {
        Report report = new Report(description, category, owner);
        reports.add(report);
        owner.getReports().add(report);
    }
}
