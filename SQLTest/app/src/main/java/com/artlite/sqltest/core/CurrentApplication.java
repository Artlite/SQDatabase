package com.artlite.sqltest.core;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.artlite.sqlib.core.SQDatabase;

/**
 * Created by dlernatovich on 12/22/2016.
 */

public final class CurrentApplication extends Application {

    /**
     * Method which provide the action when application created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SQDatabase.init(this);
    }
}
