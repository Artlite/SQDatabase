package com.artlite.sqltest.core;

import android.app.Application;

import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqltest.managers.EventManager;
import com.artlite.sqltest.managers.ThreadManager;

/**
 * Application singleton
 * Created by dlernatovich
 */

public final class CurrentApplication extends Application {

    /**
     * Method which provide the action when application created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SQDatabase.init(this);
        EventManager.init(this);
        ThreadManager.init(this);
    }
}
