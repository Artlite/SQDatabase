package com.artlite.sqltest;

import android.app.Application;

import com.artlite.sqlib.core.SQDatabase;

/**
 * Created by dlernatovich on 12/22/2016.
 */

public final class CurrentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQDatabase.init(this);
    }
}
