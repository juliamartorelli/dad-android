package com.example.julia.myapplication.Base;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.julia.myapplication.Model.User;

public class Preferences {

    private static final String SHARED_PREFERENCES = "shared_preferences";
    private static final String USER_ID = "user_id";
    private static final String USER_AUTH = "user_auth";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";

    private static Preferences instance;
    private SharedPreferences sharedPreferences;

    public static final Preferences getInstance() {

        if (instance == null) {
            instance = new Preferences();
            CustomApplication ca = CustomApplication.getInstance();
            instance.sharedPreferences = ca.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void clear() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void updateCurrentUser(final User user) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_AUTH, user.getAuthorization());
        editor.putString(USER_NAME, user.getName());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.apply();
    }

    public void setUserPreferences(final User user) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_AUTH, user.getAuthorization());
        editor.putString(USER_NAME, user.getName());
        editor.apply();
    }

    public User getCurrentUser() {

        User user = new User();
        user.setId(sharedPreferences.getString(USER_ID, ""));
        user.setName(sharedPreferences.getString(USER_NAME, ""));
        user.setAuthorization(sharedPreferences.getString(USER_AUTH, ""));
        return user;
    }

    public boolean hasUserPreferences() {

        User user = Preferences.getInstance().getCurrentUser();
        return ((user.getAuthorization() != null && !user.getAuthorization().isEmpty()) &&
                (user.getId() != null && !user.getId().isEmpty()));
    }
}