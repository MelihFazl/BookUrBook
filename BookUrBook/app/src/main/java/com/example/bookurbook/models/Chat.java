package com.example.bookurbook.models;

import java.util.ArrayList;

public class Chat
{
    //variables
    User user1;
    User user2;
    ArrayList<Message> messages;
    boolean user1Blocked;
    boolean user2Blocked;

    //constructor
    public Chat(User user1, User user2, boolean user1Blocked, boolean user2Blocked)
    {
        this.user1 = user1;
        this.user2 = user2;
        messages = new ArrayList<Message>();
        this.user1Blocked = user1Blocked;
        this.user2Blocked = user2Blocked;
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

    public boolean isUser1Blocked()
    {
        return user1Blocked;
    }

    public boolean isUser2Blocked()
    {
        return user2Blocked;
    }

    public User getUser1()
    {
        return user1;
    }

    public User getUser2()
    {
        return user2;
    }
}
