package com.example.bookurbook.model;

import android.media.Image;

import java.awt.*;
import java.io.*;
import java.util.*;

public class RegularUser extends User
{
    //constructor
    public RegularUser(String username, String email, Image avatar)
    {
        super(username,email,avatar);
    }
}