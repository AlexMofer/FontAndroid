package com.am.font.ui;

import android.app.Application;

import com.am.appcompat.app.ApplicationHolder;

public class UIApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationHolder.create(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ApplicationHolder.destroy(this);
    }
}
