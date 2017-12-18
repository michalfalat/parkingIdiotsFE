package com.example.asus.parkingidiots;

/**
 * Created by Asus on 17.12.2017.
 */

public class SendPost {
    private  String idSender;
    private String nameSender;
    private String img64Base;
    private String description;
    private String date;
    private String place;

    public SendPost(String idSender, String nameSender, String img64Base, String description, String date, String place) {
        this.idSender = idSender;
        this.nameSender = nameSender;
        this.img64Base = img64Base;
        this.description = description;
        this.date = date;
        this.place = place;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getImg64Base() {
        return img64Base;
    }

    public void setImg64Base(String img64Base) {
        this.img64Base = img64Base;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
