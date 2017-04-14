package com.artlite.sqlib.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.SQDatabaseType;

import java.lang.ref.WeakReference;

/**
 * Class which provide the init of the instance of the {@link SQDatabaseOpenHelper} for creating of the
 * SQLite database
 * Created by dlernatovich on 12/21/2016.
 */

final class SQDatabaseOpenHelper extends SQLiteOpenHelper {

    /**
     * Constants
     */
    protected static final int K_DATABASE_VERSION = 1;
    protected static final String K_DATABASE_NAME = "SQLiteArtliteBase.db";
    protected static final Exception K_ERROR_CREATE =
            new Exception("SQDatabase should be crated in android Application instance");
    /**
     * {@link Context}
     */
    protected final WeakReference<Context> contextReference;

    /**
     * Constructor for the ApplicationDatabaseHelper
     *
     * @param context current context
     */
    protected SQDatabaseOpenHelper(@NonNull final Context context) {
        super(context, K_DATABASE_NAME, null, K_DATABASE_VERSION);
        this.contextReference = new WeakReference<Context>(context);
    }

    /**
     * Method which provide the creating of the {@link SQLiteDatabase}
     *
     * @param sqLiteDatabase instance of the {@link SQLiteDatabase}
     */
    @Override
    public void onCreate(@NonNull final SQLiteDatabase sqLiteDatabase) {
    }

    /**
     * Method which provide the upgrade of {@link SQLiteDatabase}
     *
     * @param sqLiteDatabase instance of the {@link SQLiteDatabase}
     * @param oldVersion     old version
     * @param newVersion     new version
     */
    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase sqLiteDatabase,
                          int oldVersion,
                          int newVersion) {

    }

    /**
     * Method which provide the getting of the {@link Context}
     *
     * @return instance of {@link Context}
     */
    @Nullable
    public final Context getContext() {
        return (contextReference != null)
                ? contextReference.get() : null;
    }

    /**
     * Method which provide the getting of the {@link SQLiteDatabase}
     *
     * @return instance of the {@link SQLiteDatabase}
     */
    @Nullable
    public final SQLiteDatabase getDatabase(@Nullable final SQDatabaseType type) {
        SQLiteDatabase database = null;
        if (type == null) {
            return null;
        }
        switch (type) {
            case WRITE:
                database = getWritableDatabase();
            default:
                database = getReadableDatabase();
        }
//        while ((database != null)
//                || (database.isDbLockedByCurrentThread())
//                || (database.isDbLockedByOtherThreads())) {
//            //db is locked, keep looping
//            SQLogHelper.log(null, " SQLiteDatabase getDatabase(SQDatabaseType)",
//                    null, "Waiting for database release");
//        }
        return database;
    }

}
