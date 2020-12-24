package com.example.bookurbook.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Chat implements Serializable, Comparable<Chat> {
    //variables
    User user1;
    User user2;
    ArrayList<Message> messages;
    String lastMessageContentInDB;
    Date date;
    String chatID;
    boolean readByUser1;
    boolean readByUser2;

    //constructor
    public Chat(User user1, User user2, String chatID) {
        this.user1 = user1;
        this.user2 = user2;
        this.chatID = chatID;
        messages = new ArrayList<Message>();
        readByUser1 = true;
        readByUser2 = true;
    }

    //methods
    public void setMessageList(ArrayList<Message> messageList) {
        messages = messageList;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public Message getLastMessage() {
        if (messages.size() > 1) {
            return messages.get(messages.size() - 1);
        } else {
            return null;
        }
    }

    public int isConsist(User user) {
        if (user1.getUsername().equals(user.getUsername())) {
            return 1;
        } else if (user2.getUsername().equals(user.getUsername())) {
            return 2;
        } else {
            return 0;
        }
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setLastMessageContentInDB(String lastMessageContentInDB) {
        this.lastMessageContentInDB = lastMessageContentInDB;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLastMessageInFromDB() {
        return lastMessageContentInDB;
    }

    @Override
    public int compareTo(Chat o) {
        return -(getDate().compareTo(o.getDate()));
    }

    public String getChatID() {
        return chatID;
    }

    public boolean isReadByUser1() {
        return readByUser1;
    }

    public boolean isReadByUser2() {
        return readByUser2;
    }

    public void setReadByUser1(boolean readByUser1) {
        this.readByUser1 = readByUser1;
    }

    public void setReadByUser2(boolean readByUser2) {
        this.readByUser2 = readByUser2;
    }
}
