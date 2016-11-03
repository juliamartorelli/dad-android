package com.example.julia.myapplication.Base;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CustomApplication extends Application {

    private static CustomApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();

        super.onCreate();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        instance = this;
    }

    public static CustomApplication getInstance() {
        return instance;
    }
}