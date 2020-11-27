package com.example.fakebook.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String avatar;
    private FirebaseAuth firebaseAuth;
    private String name;
    private String email;
    private String dateOfBirth;
    private Boolean isMale;

    private ArrayList<Notification> notificationList=new ArrayList<>();
    private ArrayList<FriendRequests> friendRequestList=new ArrayList<>();          // uId

    private ArrayList<String> friendList;
    private HashMap<String, Integer> sentimentalRatings;  // key : uId , value : points
    public User() {
    }


    public User(String avatar, String name, String email, String dateOfBirth, Boolean isMale) {
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
        firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail();
        email=email.substring(0,email.length()-"@gmail.com".length());
        this.friendList.add(email);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ArrayList<FriendRequests> getFriendRequestList() {
        return friendRequestList;
        // lam ran doi ten di ms push dc
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