package com.example.fakebook.model;

import java.util.Date;

public class Notification {
    String email;
    String content;
    Date time;

    public Notification() {
    }

    public Notification(String email, String content, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

