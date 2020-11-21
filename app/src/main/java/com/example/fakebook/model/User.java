package com.example.fakebook.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private String uId;
    private String dateOfBirth;
    private Boolean isMale;
    private ArrayList<Notification> notificationList;
    private ArrayList<User> friendList;
    private ArrayList<FriendRequests> friendRequestList;          // uId
    private HashMap<String, Integer> sentimentalRatings;  // key : uId , value : points

    public User(String name, String uId, String dateOfBirth, Boolean isMale) {
        this.name = name;
        this.uId = uId;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<FriendRequests> getFriendRequestList() {
        return friendRequestList;
    }

    public void setFriendRequestList(ArrayList<FriendRequests> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    public HashMap<String, Integer> getSentimentalRatings() {
        return sentimentalRatings;
    }

    public void setSentimentalRatings(HashMap<String, Integer> sentimentalRatings) {
        this.sentimentalRatings = sentimentalRatings;
    }
}
