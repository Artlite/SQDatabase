package com.artlite.sqlib.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.artlite.sqlib.callbacks.SQCursorCallback;
import com.artlite.sqlib.constants.SQDatabaseType;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.log.SQLoggableObject;
import com.artlite.sqlib.model.SQFilter;
import com.artlite.sqlib.model.SQModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which provide the init of the instance of the {@link SQDatabase} for creating of the
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
     * Method which provide the init of the instance of the {@link SQDatabase}
     *
     * @param context instance of {@link Context}
     * @return created instance
     * @warning SHOULD BE CALL FROM {@link android.app.Application}
     */
    public static void init(@NonNull final Context context) {
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
     * Method which provide the delete objects
     *
     * @param objects objects
     * @param <T>     object type
     * @return execution results
     */
    public synchronized static <T extends SQModel> boolean delete(@Nullable final T... objects) {
        final String methodName = "boolean delete(objects)";
        boolean result = true;
        if (objects != null) {
            for (final T object : objects) {
                if (!delete(object)) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Method which provide the delete objects
     *
     * @param object object
     * @param <T>    object type
     * @return execution results
     */
    public synchronized static <T extends SQModel> boolean delete(@Nullable final T object) {
        final String methodName = "boolean delete(object)";
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        final String selection = String.format("%s %s", BaseColumns._ID, " LIKE ?");
        final String[] selectionArgs = {String.valueOf(object.id())};
        if (database != null) {
            try {
                database.delete(object.table(), selection, selectionArgs);
            } catch (Exception ex) {
                log(null, methodName, ex, null);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass owner class
     * @return list of {@link Cursor}
     */
    public static <T extends SQModel, K> List<T> delete(@Nullable final Class ownerClass,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final SQFilter<K>... filters) {
        return delete(ownerClass, ownerClass.getSimpleName(), callback, filters);
    }

    /**
     * Method which provide the select all functional
     *
     * @param tableName  table name
     * @param ownerClass owner class
     * @return list of {@link Cursor}
     */
    public static <T extends SQModel, K> List<T> delete(@Nullable final Class ownerClass,
                                                        @Nullable final String tableName,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final SQFilter<K>... filters) {
        final String methodName = "List<Cursor> delete(ownerClass, tableName, callback, filters)";
        final List<T> result = new ArrayList<>();
        if (ownerClass != null) {
            //Get objects
            result.addAll(select(ownerClass, tableName, callback, filters));
            //Delete objects
            try {
                final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
                final String filter = getFilter(filters);
                final String[] args = getFilterArgs(filters);
                database.delete(tableName, filter, args);
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return result;
    }

    /**
     * Method which provide the update of the {@link SQModel} objects list
     *
     * @param objects list of {@link SQModel}
     * @param <T>     objects type
     * @return update result
     */
    public static synchronized <T extends SQModel> boolean update(@Nullable final T... objects) {
        boolean result = true;
        if (validate(objects)) {
            for (final T object : objects) {
                if (!update(object)) {
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
    public synchronized static <T extends SQModel> boolean update(@Nullable final T object) {
        final String methodName = "boolean update(object)";
        final ContentValues contentValue = SQModelHelper.getContentValue(object);
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        final String selection = String.format("%s %s", BaseColumns._ID, " LIKE ?");
        final String[] selectionArgs = {String.valueOf(object.id())};
        if ((database != null) && (contentValue != null)) {
            try {
                database.update(object.table(), contentValue, selection, selectionArgs);
            } catch (Exception ex) {
                log(null, methodName, ex, null);
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * Method which provide the init SQL tables for objects
     *
     * @param object objects
     * @param <T>    objects type
     */
    protected synchronized static <T extends SQModel> boolean create(@Nullable final T object) {
        final String methodName = "boolean create(object)";
        try {
            getDatabase(SQDatabaseType.WRITE).execSQL(SQModelHelper.getCreateQuery(getContext(),
                    object));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return true;
    }

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass owner class
     * @return list of {@link Cursor}
     */
    public static <T extends SQModel, K> List<T> select(@Nullable final Class ownerClass,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final SQFilter<K>... filters) {
        List<T> result = new ArrayList<>();
        if (ownerClass != null) {
            result.addAll(select(ownerClass, ownerClass.getSimpleName(), callback, filters));
        }
        return result;
    }

    /**
     * Method which provide the select all functional
     *
     * @param tableName  table name
     * @param ownerClass owner class
     * @return list of {@link Cursor}
     */
    public static <T extends SQModel, K> List<T> select(@Nullable final Class ownerClass,
                                                        @Nullable final String tableName,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final SQFilter<K>... filters) {
        final String methodName = "List<Cursor> select(tableName, ownerClass)";
        List<T> result = new ArrayList<>();
        try {
            final SQLiteDatabase database = getDatabase(SQDatabaseType.READ);
            final String[] projection = SQModelHelper.generateProjection(getContext(), ownerClass);
            final String filter = getFilter(filters);
            final String[] args = getFilterArgs(filters);
            final Cursor cursor = database.query(tableName, projection,
                    filter, args, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    try {
                        final T object = callback.convert(cursor);
                        result.add(object);
                    } catch (Exception ce) {
                        log(null, methodName, ce, null);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting of the filter query
     *
     * @param filters filter objects
     * @param <T>     objects type
     * @return filter query
     */
    @NonNull
    protected static <T> String getFilter(@Nullable final SQFilter<T>... filters) {
        final StringBuilder result = new StringBuilder();
        List<String> queryList = new ArrayList<>();
        if (validate(filters)) {
            for (final SQFilter filter : filters) {
                if (validate(filter)) {
                    final String filterValue = filter.getFilter();
                    if (validate(filterValue)) {
                        queryList.add(filterValue);
                    }
                }
            }
        }
        if (queryList.size() > 0) {
            result.append(TextUtils.join(", ", queryList));
        }
        return result.toString();
    }

    /**
     * Method which provide the getting of the filter query
     *
     * @param filters filter objects
     * @param <T>     objects type
     * @return filter query
     */
    @NonNull
    protected static <T> String[] getFilterArgs(@Nullable final SQFilter<T>... filters) {
        List<String> queryList = new ArrayList<>();
        if (validate(filters)) {
            for (final SQFilter filter : filters) {
                if (validate(filter)) {
                    final String filterValue = filter.getFilterArgs();
                    if (validate(filterValue)) {
                        queryList.add(filterValue);
                    }
                }
            }
        }
        return queryList.toArray(new String[queryList.size()]);
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

    /**
     * Method which provide the getting of the {@link Context}
     *
     * @return instance of {@link Context}
     */
    @Nullable
    protected static Context getContext() {
        final String methodName = "Context getContext()";
        try {
            return instance.openHelper.getContext();
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return null;
    }

}
