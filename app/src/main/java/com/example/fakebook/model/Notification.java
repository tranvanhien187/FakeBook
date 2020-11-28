package com.example.fakebook.model;

public class Notification {
    String email;
    String content;
    String time;

    public Notification() {
    }

    public Notification(String email, String content, String time) {
        this.email = email;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

