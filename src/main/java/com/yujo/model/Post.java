package com.yujo.model;

import com.yujo.util.IMappable;

/**
 *this class represents the tabular entity posts on our database
 */
public class Post implements IMappable {

   

    private int id;
    private User user;
    private String content;
    private String content_time;
    private String image;


    public Post( int id, User user, String content, String content_time, String image ) {
        super();
        this.id = id;
        this.user = user;
        this.content = content;
        this.content_time = content_time;
        this.image = image;
    }

    public Post() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getContent_time() {
        return content_time;
    }

    public void setContent_time( String content_time ) {
        this.content_time = content_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image ) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", user:" + user + ", content:" + content + ", content_time:" + content_time + ", image:"
                + image + "}";
    }
}