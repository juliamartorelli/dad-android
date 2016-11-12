package com.example.julia.myapplication.Base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomApplication extends Application {

    private static CustomApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(new SampleDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        instance = this;
    }

    private static class SampleDumperPluginsProvider implements DumperPluginsProvider {

        Context context;

        public SampleDumperPluginsProvider (Context context) {
            this.context = context;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            ArrayList<DumperPlugin> plugins = new ArrayList<>();
            for(DumperPlugin defaultPlugin : Stetho.defaultDumperPluginsProvider(context).get()) {
                plugins.add(defaultPlugin);
            }

            return plugins;
        }
    }

    public static CustomApplication getInstance() {
        return instance;
    }

}