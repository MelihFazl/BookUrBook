import java.awt.*;
import java.io.*;
import java.util.*;

public class UserList
{

	private ArrayList<User> userArray;
	//!!!!!!!!private FirebaseFirestore database;
	//!!!!!!!!!private FirebaseAuth auth;

	public UserList()
	{
		userArray = new ArrayList<>();
	}

	public void createUser(String username, String email, Image avatar, boolean isAdmin)//!!!!PASSWORD AS PARAMETER - DATABASE?
	{
		if(isAdmin)
		{
			Admin admin = new Admin(username, email, avatar);
			addUser(admin);
		}
		else
		{
			RegularUser regUser = new RegularUser(username, email, avatar);
			addUser(regUser);
		}
	}

	public User findUserByUsername(String username)
	{
		for(User user: userArray)
		{
			if(user.getUsername().equals(username));
				return user;
		}
		return null;
	}

	public void addUser(User user)
	{
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void deleteUser(User user)
	{
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
