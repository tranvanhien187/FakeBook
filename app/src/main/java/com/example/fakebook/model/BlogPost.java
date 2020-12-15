package com.example.fakebook.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class BlogPost {
    public String userId,imageUrl,text,namename;
    public Date timestamp;
    public boolean liked=false;
    public BlogPost(){}

    public BlogPost(String userId, String imageUrl, String text, Date timestamp,String namename) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.text = text;
        this.timestamp=timestamp;
        this.namename=namename;
    }

    public String getNamename() {
        return namename;
    }

    public void setNamename(String namename) {
        this.namename = namename;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
