package com.example.ModelClasses;

import android.media.Image;

import java.awt.*;
import java.io.*;
import java.util.*;

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
