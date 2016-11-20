package com.example.julia.myapplication.Util;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ActionBarDrawerToggle extends android.support.v7.app.ActionBarDrawerToggle {

    private Runnable runnable;

    public ActionBarDrawerToggle(final Activity activity, final DrawerLayout drawerLayout, final Toolbar toolbar, final int openDrawerContentDescRes, final int closeDrawerContentDescRes) {

        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    @Override
    public void onDrawerOpened(final View drawerView) {

        super.onDrawerOpened(drawerView);
    }

    @Override
    public void onDrawerClosed(final View view) {

        super.onDrawerClosed(view);
    }

    @Override
    public void onDrawerStateChanged(final int newState) {

        super.onDrawerStateChanged(newState);
        if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
            runnable.run();
            runnable = null;
        }
    }

    public void runWhenIdle(final Runnable runnable) {

        this.runnable = runnable;
    }
}
