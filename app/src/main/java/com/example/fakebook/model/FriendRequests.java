package com.example.fakebook.model;

public class FriendRequests {
    String name;
    String uId;
    int avatar;
    String time;

    public FriendRequests() {
    }

    public FriendRequests(String name, String uId, int avatar, String time) {
        this.name = name;
        this.uId = uId;
        this.avatar = avatar;
        this.time = time;
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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
