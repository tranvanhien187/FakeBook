package com.example.fakebook.model;

public class FriendRequests {
    String email;
    String time;

    public FriendRequests() {
    }

    public FriendRequests(String email, String time) {
        this.email = email;
        this.time = time;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
