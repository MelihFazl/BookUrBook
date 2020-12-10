import java.awt.*;
import java.io.*;
import java.util.*;
package com.example.ModelClasses;

public class PostList implements Filterable, Sortable
{
	//instance variables
	private ArrayList<Post> postArray;

	//constructor
	public PostList()
	{
		postArray = new ArrayList<>();
	}

	//get methods
	public ArrayList<Post> getPostArray()
	{
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	//set methods
	public void setPostArray(PostList postList)
	{
		postArray = postList.getPostArray();
	}

	//other methods

	/**
	 * Adds post to the postArray
	 * @param post post to be added
	 */
	public void addPost(Post post)
	{
		postArray.add(post);
	}

	/**
	 * Deletes the post from the postArray
	 * @param post post to be deleted
	 */
	public void deletePost(Post post)
	{
		for (Iterator<Post> iterator = postArray.iterator(); iterator.hasNext(); )
		{
			Post listPost = iterator.next();
			if (listPost == post) {
				iterator.remove();
			}
		}
	}

	/**
	 * This method creates a post, adds it to the Postlist and also adds it to the userSpecPostList of the owner
	 * @param description .
	 * @param title .
	 * @param university .
	 * @param course .
	 * @param price .
	 * @param avatar .
	 * @param owner .
	 */
	public void createPost(String description, String title, String university, String course, double price, Image avatar, User owner)
	{
		Post post = new Post(description,title,university,course,price,avatar,owner);
		this.addPost(post);
		owner.getUserPostList().addPost(post);
	}

	/**
	 * When a post is sold, it deletes it from the post list and marks it as sold.
	 * @param post post that is sold
	 */
	public void postSold(Post post)
	{
		deletePost(post);
		post.setSold(true);
	}

	@Override
	public void sortByPrice(boolean isLowToHigh)
	{
		if(isLowToHigh)
		{
			Collections.sort(postArray, new PostPriceComparator());
		}
		else
		{
			Collections.sort(postArray,new PostPriceComparator());
			Collections.reverse(postArray);
		}
	}

	@Override
	public void sortByLetter(boolean isAToZ)
	{
		if(isAToZ)
		{
			Collections.sort(postArray, new PostTitleComparator());
		}
		else
		{
			Collections.sort(postArray,new PostTitleComparator());
			Collections.reverse(postArray);
		}
	}

	@Override
	public PostList filterByUniversity(String University)
	{
		PostList filtered = new PostList();
		for (Post post : postArray)
		{
			if (post.getUniversity().equals(University))
				filtered.addPost(post);
		}
		return filtered;
	}

	@Override
	public PostList filterByCourse(String course)
	{
		PostList filtered = new PostList();
		for (Post post : postArray)
		{
			if (post.getCourse().equals(course))
				filtered.addPost(post);
		}
		return filtered;
	}

	@Override
	public PostList filterByPrice(int min, int max)
	{
		PostList filtered = new PostList();
		for (Post post : postArray)
		{
			if (post.getPrice() <= max && min <= post.getPrice())
				filtered.addPost(post);
		}
		return filtered;
	}

	@Override
	public PostList filterByOwner(User owner)
	{
		PostList filtered = new PostList();
		for (Post post : postArray)
		{
			if (post.getOwner().getEmail().equals(owner.getEmail()))
				filtered.addPost(post);
		}
		return filtered;
	}
}
