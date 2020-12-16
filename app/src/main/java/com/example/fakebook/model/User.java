package com.example.fakebook.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String avatar;
    private String name;
    private String email;
    private String dateOfBirth;
    private Boolean isMale;
    private String lowercaseName;
    private ArrayList<Notification> notificationList=new ArrayList<>();
    private ArrayList<String> friendList=new ArrayList<>();
    private HashMap<String,FriendRequests> friendRequestList=new HashMap<>();
    private HashMap<String,FriendRequests> sendFriendRequestList=new HashMap<>();
    private HashMap<String, Integer> sentimentalRatings = new HashMap<>();  // key : uId , value : points
    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String avatar, String name, String email, String dateOfBirth, Boolean isMale) {
        this.avatar=avatar;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
        this.lowercaseName=name.toLowerCase();
    }

    public String getLowercaseName() {
        return lowercaseName;
    }

    public void setLowercaseName(String lowercaseName) {
        this.lowercaseName = lowercaseName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public HashMap<String,FriendRequests> getFriendRequestList() {
        return friendRequestList;
        // lam ran doi ten di ms push dc
    }

    public void setFriendRequestList(HashMap<String,FriendRequests> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    public HashMap<String, Integer> getSentimentalRatings() {
        return sentimentalRatings;
    }

    public void setSentimentalRatings(HashMap<String, Integer> sentimentalRatings) {
        this.sentimentalRatings = sentimentalRatings;
    }

    public HashMap<String, FriendRequests> getSendFriendRequestList() {
        return sendFriendRequestList;
    }

    public void setSendFriendRequestList(HashMap<String, FriendRequests> sendFriendRequestList) {
        this.sendFriendRequestList = sendFriendRequestList;
    }
}
