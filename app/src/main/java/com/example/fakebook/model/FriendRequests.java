package com.example.fakebook.model;

import java.util.Date;

public class FriendRequests {
    String email;
    Date time;

    public FriendRequests() {
    }

    public FriendRequests(String email, Date time) {
        this.email = email;
        this.time = time;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
