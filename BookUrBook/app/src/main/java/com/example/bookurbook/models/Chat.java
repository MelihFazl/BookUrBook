package com.example.bookurbook.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Chat implements Serializable
{
    //variables
    User user1;
    User user2;
    ArrayList<Message> messages;
    String lastMessageContentInDB;

    //constructor
    public Chat(User user1, User user2)
    {
        this.user1 = user1;
        this.user2 = user2;
        messages = new ArrayList<Message>();
    }

    //methods
    public void setMessageList( ArrayList<Message> messageList )
    {
        messages = messageList;
    }

    public ArrayList<Message> getMessages()
    {
        return messages;
    }

    public void addMessage(Message msg )
    {
        messages.add(msg);
    }

    public Message getLastMessage()
    {
        if (messages.size() > 1)
        {
            return messages.get(messages.size() - 1);
        }
        else
        {
            return null;
        }
    }

    public int isConsist( User user )
    {
        if ( user1.getUsername().equals(user.getUsername()) )
        {
            return 1;
        }
        else if ( user2.getUsername().equals(user.getUsername()) )
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    public User getUser1()
    {
        return user1;
    }

    public User getUser2()
    {
        return user2;
    }

    public void setLastMessageContentInDB(String lastMessageContentInDB)
    {
        this.lastMessageContentInDB = lastMessageContentInDB;
    }

    public String getLastMessageInFromDB()
    {
        return lastMessageContentInDB;
    }

}
