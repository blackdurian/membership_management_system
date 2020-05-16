package main.java.controllers;

import main.java.models.users.UserLogin;

import java.text.SimpleDateFormat;

public class Season {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static UserLogin user;
    public static void setUser(UserLogin user) {
       Season.user = user;
    }

    public static String getUserId() {
        return Season.user.getId();
    }

    public static String getUsername() {
        return Season.user.getUsername();
    }

    public static String getUserType() {
        // ToDO: return ROLE only
        return Season.user.getType();
    }
}
