package com.example.asus.parkingidiots;

/**
 * Created by Asus on 30.10.2017.
 */

public class Post {
    String userName;
    String date;
    String img;
    String authorText;
    Long likes;
    Long Views;

    public Post(String userName, String date, String img, String authorText, Long likes, Long views) {
        this.userName = userName;
        this.date = date;
        this.img = img;
        this.authorText = authorText;
        this.likes = likes;
        Views = views;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthorText() {
        return authorText;
    }

    public void setAuthorText(String authorText) {
        this.authorText = authorText;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getViews() {
        return Views;
    }

    public void setViews(Long views) {
        Views = views;
    }
}
