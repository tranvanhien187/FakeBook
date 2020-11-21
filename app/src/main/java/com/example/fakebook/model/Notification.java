package com.example.fakebook.model;

public class Notification {
    String content;
    String time;
    int avatar;

    public Notification() {
    }

    public Notification(String content, String time, int avatar) {
        this.content = content;
        this.time = time;
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}

