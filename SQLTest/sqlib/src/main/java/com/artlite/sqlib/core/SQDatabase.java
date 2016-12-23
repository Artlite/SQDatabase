package com.artlite.sqlib.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.log.SQLoggableObject;
import com.artlite.sqlib.model.SQModel;

import java.util.List;

/**
 * Class which provide the create of the instance of the {@link SQDatabase} for creating of the
 * SQLite database
 * Created by dlernatovich on 12/21/2016.
 */

public final class SQDatabase extends SQLoggableObject {

    /**
     * Instance
     */
    private static SQDatabase instance;

    /**
     * Database
     */
    private final SQDatabaseOpenHelper openHelper;
    private final SQLiteDatabase database;

    /**
     * Constructor for the ApplicationDatabaseHelper
     *
     * @param context current context
     */
    protected SQDatabase(@NonNull final Context context) {
        this.openHelper = new SQDatabaseOpenHelper(context);
        this.database = this.openHelper.getDatabase();
    }

    /**
     * Method which provide the create of the instance of the {@link SQDatabase}
     *
     * @param context instance of {@link Context}
     * @return created instance
     * @warning SHOULD BE CALL FROM {@link android.app.Application}
     */
    public static void create(@NonNull final Context context) {
        if (instance == null) {
            instance = new SQDatabase(context);
        }
    }

    /**
     * Method which provide the
     *
     * @param objects objects
     * @param <T>     objects type
     */
    public static <T extends SQModel> boolean insert(@Nullable final T... objects) {
        boolean result = true;
        if (validate(objects)) {
            for (final T object : objects) {
                if (!insert(object)) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Method which provide the
     *
     * @param object objects
     * @param <T>    objects type
     */
    public static <T extends SQModel> boolean insert(@Nullable final T object) {
        final String methodName = "boolean insert(object)";
        ContentValues contentValue = SQModelHelper.getContentValue(object);
        if ((getDatabase() != null) && (contentValue != null)) {
            try {
                if (create(object)) {
                    getDatabase().insert(object.table(), null, contentValue);
                }
            } catch (Exception ex) {
                log(null, methodName, ex, null);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Method which provide the create SQL tables for objects
     *
     * @param object objects
     * @param <T>    objects type
     */
    protected static <T extends SQModel> boolean create(@Nullable final T object) {
        final String methodName = "boolean create(object)";
        try {
            getDatabase().execSQL(SQModelHelper.getCreateQuery(object));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return false;
    }

    /**
     * Method which provide the getting of the {@link SQLiteDatabase}
     *
     * @return instance of the {@link SQLiteDatabase}
     */
    @Nullable
    protected static SQLiteDatabase getDatabase() {
        if ((instance != null) && (instance.database != null)) {
            return instance.database;
        }
        return null;
    }

}
