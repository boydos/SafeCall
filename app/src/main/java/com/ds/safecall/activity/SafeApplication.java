package com.ds.safecall.activity;

import android.app.Application;

public class SafeApplication extends Application {

    public static SafeApplication instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static SafeApplication getApplication() {
        return instance;
    }
}
