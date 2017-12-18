package com.example.asus.parkingidiots;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Asus on 16.12.2017.
 */

public class ConfigContainer {


    private static Context context;
    private static SharedPreferences mPrefs;


    public static void init(Context c) {
        context = c;
        mPrefs = c.getSharedPreferences("app",MODE_PRIVATE);
    }


    public static void saveUser(String name){
        UserModel u = new UserModel(name, UUID.randomUUID().toString());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String jsonConf = gson.toJson(u);
        prefsEditor.putString("USER", jsonConf);
        prefsEditor.commit();
    }


    public static UserModel getUser () {
        Gson gson = new Gson();
        String json = mPrefs.getString("USER", "");
        UserModel user = gson.fromJson(json, UserModel.class);
        return user;
    }
}
