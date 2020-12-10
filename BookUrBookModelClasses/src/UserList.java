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

	public void createUser(String username, String email, String password, Image avatar, boolean isAdmin)
	{
		User user = new User()
	}

	public User findUserByUsername(String username)
	{
		throw new UnsupportedOperationException("The method is not implemented yet.");
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
