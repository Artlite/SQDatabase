package com.artlite.sqltest.managers;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Class which provide the executing action in background and main threads
 * Created by dlernatovich
 */

public final class ThreadManager {
    private static final String TAG = ThreadManager.class.getName();

    private static ThreadManager instance;
    private final Handler handler;

    /**
     * Method which provide the getting instance of {@link ThreadManager}
     *
     * @return
     */
    private static ThreadManager getInstance() {
        if (instance == null) {
            Log.e(TAG, "ThreadManager should be initialized in the Application singleton");
        }
        return instance;
    }

    /**
     * Method which provide the initializing of {@link ThreadManager}
     *
     * @param context instance of {@link Context}
     * @return initialization result
     * @warning should be initializing in application singleton
     */
    public static void init(@Nullable final Context context) {
        if (instance == null) {
            instance = new ThreadManager(context);
        } else {
            Log.e(TAG, "ThreadManager already initialized");
        }
    }

    /**
     * Default constructor
     */
    private ThreadManager(@Nullable final Context context) {
        handler = new Handler();
    }

    /**
     * Method which provide the executing action on main thread
     *
     * @param callback instance of {@link OnThreadCallback}
     */
    public static void main(@Nullable final OnThreadCallback callback) {
        //Validate instance
        if (!validateInstance()) {
            return;
        }
        //Execute callback
        if (getInstance().handler != null) {
            getInstance().handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        callback.onExecute();
                    }
                }
            });
        }
    }

    /**
     * Method which provide the executing action on background thread
     *
     * @param callback instance of {@link OnThreadCallback}
     */
    public static void background(@Nullable final OnThreadCallback callback) {
        //Validate instance
        if (!validateInstance()) {
            return;
        }
        //Execute callback
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onExecute();
                }
            }
        }).start();
    }

    /**
     * Method which provide the executing action on background thread
     *
     * @param callback instance of {@link OnThreadCallback}
     */
    public static void execute(@Nullable final OnExecutionCallback callback) {
        //Validate instance
        if (!validateInstance()) {
            return;
        }
        background(new OnThreadCallback() {
            @Override
            public void onExecute() {
                if (callback != null) {
                    callback.onBackground();
                    main(new OnThreadCallback() {
                        @Override
                        public void onExecute() {
                            if (callback != null) {
                                callback.onMain();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Method which provide the validation initializing
     *
     * @return validation result
     */
    private static boolean validateInstance() {
        return getInstance() != null;
    }

    //==============================================================================================
    //                                      CLASSES
    //==============================================================================================

    /**
     * Callback class
     */
    public interface OnThreadCallback {
        /**
         * Method which provide the executing callback
         */
        void onExecute();
    }

    /**
     * Callback which provide the sequently executing on background and on main thread and
     */
    public interface OnExecutionCallback {
        /**
         * Method which provide the background executing
         */
        void onBackground();

        /**
         * Method which provide the main thread executing
         */
        void onMain();
    }
}
