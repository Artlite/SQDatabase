package com.artlite.sqlib.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.SQDatabaseType;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.log.SQLoggableObject;
import com.artlite.sqlib.model.SQModel;

import java.util.ArrayList;
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

    /**
     * Constructor for the ApplicationDatabaseHelper
     *
     * @param context current context
     */
    protected SQDatabase(@NonNull final Context context) {
        this.openHelper = new SQDatabaseOpenHelper(context);
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
    public synchronized static <T extends SQModel> boolean insert(@Nullable final T... objects) {
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
    public synchronized static <T extends SQModel> boolean insert(@Nullable final T object) {
        final String methodName = "boolean insert(object)";
        final ContentValues contentValue = SQModelHelper.getContentValue(object);
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        if ((database != null) && (contentValue != null)) {
            try {
                if (create(object)) {
                    database.insertOrThrow(object.table(), null, contentValue);
                    return true;
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
    protected synchronized static <T extends SQModel> boolean create(@Nullable final T object) {
        final String methodName = "boolean create(object)";
        try {
            getDatabase(SQDatabaseType.WRITE).execSQL(SQModelHelper.getCreateQuery(object));
            return true;
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
    }

    /**
     * Method which provide the select all functional
     *
     * @param tableName  table name
     * @param ownerClass owner class
     * @return list of {@link Cursor}
     */
    public static List<Cursor> selectAll(@Nullable final String tableName,
                                         @Nullable final Class ownerClass) {
        final String methodName = "List<Cursor> selectAll(tableName, ownerClass)";
        List<Cursor> result = new ArrayList<>();
        try {
            final SQLiteDatabase database = getDatabase(SQDatabaseType.READ);
            final String[] projection = SQModelHelper.generateProjection(ownerClass);
            final Cursor cursor = database.query(tableName, projection,
                    null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    result.add(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting of the {@link SQLiteDatabase}
     *
     * @return instance of the {@link SQLiteDatabase}
     */
    @Nullable
    protected static SQLiteDatabase getDatabase(@Nullable final SQDatabaseType type) {
        if ((instance != null) && (instance.openHelper != null)) {
            return instance.openHelper.getDatabase(type);
        }
        return null;
    }

}
