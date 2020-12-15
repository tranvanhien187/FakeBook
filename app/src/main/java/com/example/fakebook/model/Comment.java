package com.example.fakebook.model;

import java.util.Date;

public class Comment {
    private String id,name,url;
    private String text;
    private Date time;

    public Comment(){}

    public Comment(String id, String name, String url, String text,Date time) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.text = text;
        this.time=time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
