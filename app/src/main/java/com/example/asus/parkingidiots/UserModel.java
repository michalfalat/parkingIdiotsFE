package com.example.asus.parkingidiots;

/**
 * Created by Asus on 16.12.2017.
 */

public class UserModel {

    private String name;
    private String id;

    public UserModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
