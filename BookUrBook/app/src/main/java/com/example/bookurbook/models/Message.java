package com.example.bookurbook.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Message implements Serializable
{
    //variables
    SimpleDateFormat messageDate;
    SimpleDateFormat messageTime;
    User sentBy;
    String content;

    //constructor
    public Message ( SimpleDateFormat messageDate, SimpleDateFormat messageTime, User sentBy, String content )
    {
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.content = content;
        this.sentBy = sentBy;
    }

    //methods
    public SimpleDateFormat getMessageDate()
    {
        return messageDate;
    }

    public SimpleDateFormat getMessageTime()
    {
        return messageTime;
    }

    public String getContent()
    {
        return content;
    }

    public User getSentBy()
    {
        return sentBy;
    }

    public void setMessageDate(SimpleDateFormat messageDate)
    {
        this.messageDate = messageDate;
    }

    public void setMessageTime(SimpleDateFormat messageTime)
    {
        this.messageTime = messageTime;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setSentBy(User sentBy)
    {
        this.sentBy = sentBy;
    }
}
