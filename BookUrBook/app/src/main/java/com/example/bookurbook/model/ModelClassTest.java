package com.example.bookurbook.model;
/**
 * This method is created in order to test the model class
 * @author Kerem ŞAHİN
 * @version 1
 */
public class ModelClassTest
{
    public static void main(String[] args)
    {
        //variables
        UserList list;
        PostList list2;

        //test code
        list = new UserList();
        list2 = new PostList();
        list.createUser("Ahmet","ahmet@gmail.com",null,false);
        list.createUser("Mehmet","mehmet@gmail.com",null,true);
        list2.createPost("enfes ürün","Bakarlar","Bilkent","Math",22,null,list.findUserByUsername("Ahmet"));
    }
}

