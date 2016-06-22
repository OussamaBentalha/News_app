package com.esgi.android.news.metier.utils;

import android.app.Application;

import com.esgi.android.news.metier.model.User;

/**
 * Created by Sam on 22/06/16.
 */
public class App extends Application {

    private static User mUserConnected = null;

    public static User getmUserConnected() {
        return mUserConnected;
    }

    public static void setmUserConnected(User mUserConnected) {
        App.mUserConnected = mUserConnected;
    }
}
