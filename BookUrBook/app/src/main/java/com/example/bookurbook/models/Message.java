package com.example.bookurbook.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable, Comparable<Message> {
    //variables
    String messageDate;
    String messageTime;
    String sentBy;
    String content;
    Date date;

    //constructor
    public Message(String sentBy, String contentString, String messageDate, String messageTime) {
        this.messageDate = messageDate;
        this.messageTime = messageTime;
        this.content = contentString;
        this.sentBy = sentBy;
    }

    //methods
    public String getMessageDate() {
        return messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getContent() {
        return content;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Message o) {
        return this.getDate().compareTo(o.getDate());
    }
}
