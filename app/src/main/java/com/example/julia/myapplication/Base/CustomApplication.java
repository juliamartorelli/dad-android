package com.example.julia.myapplication.Base;

import android.app.Application;

public class CustomApplication extends Application {

    private static CustomApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static CustomApplication getInstance() {

        return instance;
    }
}