package com.artlite.sqlib.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.callbacks.SQAnnotationClassCallback;
import com.artlite.sqlib.callbacks.SQCursorCallback;
import com.artlite.sqlib.constants.SQDatabaseType;
import com.artlite.sqlib.constants.SQFilterDelimiter;
import com.artlite.sqlib.constants.SQType;
import com.artlite.sqlib.helpers.annotation.SQAnnotationHelper;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.log.SQLoggableObject;
import com.artlite.sqlib.model.SQFilter;
import com.artlite.sqlib.model.SQModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * Field which provide the checking if {@link SQLiteDatabase} is open for writing
     */
    private AtomicBoolean isOpenWriting = new AtomicBoolean(false);

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

    //==============================================================================================
    //                                      CREATE
    //==============================================================================================

    /**
     * Method which provide the init SQL tables for objects
     *
     * @param object   instance of {@link Object}
     * @param database instance of {@link SQLiteDatabase}
     * @param <T>      class type
     * @return creation result
     */
    protected static <T extends SQModel> boolean create(@Nullable final T object,
                                                        @Nullable final SQLiteDatabase database) {
        final String methodName = "boolean create(object)";
        try {
            database.execSQL(SQModelHelper.getCreateQuery(getContext(),
                    object));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return true;
    }
    //==============================================================================================
    //                                      INSERT
    //==============================================================================================

    /**
     * Method which provide the inserting object into {@link SQDatabase}
     *
     * @param objects {@link List} of the {@link SQModel}
     * @param <T>     class type
     * @return inserting result
     */
    public static <T extends SQModel> boolean insert(@Nullable final List<T> objects) {
        if (validate(objects)) {
            return insert(objects.toArray(new SQModel[0]));
        }
        return false;
    }

    /**
     * Method which provide the inserting object into {@link SQDatabase}
     *
     * @param objects array of the {@link SQModel}
     * @param <T>     class type
     * @return inserting result
     */
    public static <T extends SQModel> boolean insert(@Nullable final T... objects) {
        boolean result = true;
        if (validate(objects)) {
            final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
            for (final T object : objects) {
                if (!insert(object, database)) {
                    result = false;
                }
            }
            releaseDatabase();
        }
        return result;
    }

    /**
     * Method which provide the inserting object into {@link SQDatabase}
     *
     * @param object instance of {@link SQModel}
     * @param <T>    class type
     * @return inserting result
     */
    public static <T extends SQModel> boolean insert(@Nullable final T object) {
        final String methodName = "boolean insert(object)";
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        boolean result = insert(object, database);
        releaseDatabase();
        return result;
    }

    /**
     * Method which provide the inserting object into {@link SQDatabase}
     *
     * @param object   instance of {@link SQModel}
     * @param database instance of {@link SQLiteDatabase}
     * @param <T>      class type
     * @return inserting result
     */
    protected static <T extends SQModel> boolean insert(@Nullable final T object,
                                                        @Nullable final SQLiteDatabase database) {
        final String methodName = "boolean insert(object)";
        try {
            final ContentValues contentValue = SQModelHelper.getContentValue(object);
            if (create(object, database)) {
                database.insertOrThrow(object.table(), null, contentValue);
            }
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return true;
    }

    //==============================================================================================
    //                                      DELETE
    //==============================================================================================

    /**
     * Method which provide the delete objects
     *
     * @param objects {@link List} of the {@link SQModel}
     * @param <T>     class type
     * @return deleting result
     */
    public static <T extends SQModel> boolean delete(@Nullable final List<T> objects) {
        if (validate(objects)) {
            return delete(objects.toArray(new SQModel[0]));
        }
        return false;
    }

    /**
     * Method which provide the delete objects
     *
     * @param objects array of {@link SQModel}
     * @param <T>     class type
     * @return deleting result
     */
    public static <T extends SQModel> boolean delete(@Nullable final T... objects) {
        final String methodName = "boolean delete(objects)";
        boolean result = true;
        if (validate(objects)) {
            final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
            for (final T object : objects) {
                if (!delete(object, database)) {
                    result = false;
                }
            }
            releaseDatabase();
        }
        return result;
    }

    /**
     * Method which provide the delete objects
     *
     * @param object instance of {@link SQModel}
     * @param <T>    class type
     * @return deleting result
     */
    public static <T extends SQModel> boolean delete(@Nullable final T object) {
        final String methodName = "boolean delete(object)";
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        boolean result = delete(object, database);
        releaseDatabase();
        return result;
    }

    /**
     * Method which provide the delete objects
     *
     * @param object   instance of {@link SQModel}
     * @param database instance of {@link SQLiteDatabase}
     * @param <T>      class type
     * @return deleting result
     */
    public static <T extends SQModel> boolean delete(@Nullable final T object,
                                                     @Nullable final SQLiteDatabase database) {
        final String methodName = "boolean delete(object)";
        try {
            final String selection = String.format("%s %s", BaseColumns._ID, " LIKE ?");
            final String[] selectionArgs = {String.valueOf(object.id())};
            log(null, methodName, null, "[DB] DELETE " + selection);
            log(null, methodName, null, "[DB] DELETE ARGS " + Arrays.deepToString(selectionArgs));
            database.delete(object.table(), selection, selectionArgs);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return true;
    }

    //==============================================================================================
    //                                   DELETE BY FILTER
    //==============================================================================================

    /**
     * Method which provide the deleting functional by {@link SQFilter}
     *
     * @param ownerClass instance of {@link Class}
     * @param filters    {@link List} of the {@link SQFilter}
     * @param <T>        class type
     * @return deleting result
     */
    public static <T extends SQModel> int delete(@Nullable final Class ownerClass,
                                                 @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return delete(ownerClass, filters.toArray(new SQFilter[0]));
        }
        return delete(ownerClass);
    }

    /**
     * Method which provide the deleting functional by {@link SQFilter}
     *
     * @param ownerClass instance of {@link Class}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return deleting result
     */
    public static <T extends SQModel> int delete(@Nullable final Class ownerClass,
                                                 @Nullable final SQFilter... filters) {
        if (validate(ownerClass)) {
            return delete(ownerClass, ownerClass.getSimpleName(), filters);
        }
        return 0;
    }

    /**
     * Method which provide the deleting functional by {@link SQFilter}
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param filters    {@link List} of the {@link SQFilter}
     * @param <T>        class type
     * @return deleting result
     */
    public static <T extends SQModel> int delete(@Nullable final Class ownerClass,
                                                 @Nullable final String tableName,
                                                 @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return delete(ownerClass, tableName, filters.toArray(new SQFilter[0]));
        }
        return delete(ownerClass, tableName);
    }

    /**
     * Method which provide the deleting functional by {@link SQFilter}
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return deleting result
     */
    public static <T extends SQModel> int delete(@Nullable final Class ownerClass,
                                                 @Nullable final String tableName,
                                                 @Nullable final SQFilter... filters) {
        final String methodName = "List<Cursor> delete(ownerClass, tableName, callback, filters)";
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        int result = delete(ownerClass, tableName, database, filters);
        releaseDatabase();
        return result;
    }

    /**
     * Method which provide the deleting functional by {@link SQFilter}
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param database   instance of {@link SQLiteDatabase}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return deleting result
     */
    protected static <T extends SQModel> int delete(@Nullable final Class ownerClass,
                                                    @Nullable final String tableName,
                                                    @Nullable final SQLiteDatabase database,
                                                    @Nullable final SQFilter... filters) {
        final String methodName = "List<Cursor> delete(ownerClass, tableName, callback, filters)";
        int result = 0;
        if (ownerClass != null) {
            //Delete objects
            try {
                final String filter = getFilter(filters);
                final String[] args = getFilterArgs(filters);
                log(null, methodName, null, "[DB] DELETE " + filter);
                log(null, methodName, null, "[DB] DELETE ARGS " + Arrays.deepToString(args));
                result = database.delete(tableName, filter, args);
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return result;
    }

    //==============================================================================================
    //                                      UPDATE
    //==============================================================================================

    /**
     * Method which provide the update of the {@link SQModel} objects list
     *
     * @param objects {@link List} of the {@link SQModel}
     * @param <T>     class type
     * @return updating result
     */
    public static <T extends SQModel> boolean update(@Nullable final List<T> objects) {
        if (validate(objects)) {
            return update(objects.toArray(new SQModel[0]));
        }
        return false;
    }

    /**
     * Method which provide the update of the {@link SQModel} objects list
     *
     * @param objects array of {@link SQModel}
     * @param <T>     class type
     * @return updating result
     */
    public static <T extends SQModel> boolean update(@Nullable final T... objects) {
        boolean result = true;
        if (validate(objects)) {
            final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
            for (final T object : objects) {
                if (!update(object, database)) {
                    result = false;
                }
            }
            releaseDatabase();
        }
        return result;
    }

    /**
     * Method which provide the update of the {@link SQModel} objects list
     *
     * @param object instance of {@link SQModel}
     * @param <T>    class type
     * @return updating result
     */
    public static <T extends SQModel> boolean update(@Nullable final T object) {
        final String methodName = "boolean update(object)";
        final SQLiteDatabase database = getDatabase(SQDatabaseType.WRITE);
        boolean result = update(object, database);
        releaseDatabase();
        return result;
    }

    /**
     * Method which provide the update of the {@link SQModel} objects list
     *
     * @param object   instance of {@link SQModel}
     * @param database instance of {@link SQLiteDatabase}
     * @param <T>      class type
     * @return updating result
     */
    public static <T extends SQModel> boolean update(@Nullable final T object,
                                                     @Nullable final SQLiteDatabase database) {
        final String methodName = "boolean update(object)";
        try {
            final ContentValues contentValue = SQModelHelper.getContentValue(object);
            final String selection = String.format("%s %s", BaseColumns._ID, " LIKE ?");
            final String[] selectionArgs = {String.valueOf(object.id())};
            log(null, methodName, null, "[DB] UPDATE " + selection);
            log(null, methodName, null, "[DB] UPDATE ARGS " + Arrays.deepToString(selectionArgs));
            database.update(object.table(), contentValue, selection, selectionArgs);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
            return false;
        }
        return true;
    }

    //==============================================================================================
    //                                      SEARCH
    //==============================================================================================

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param query      search query
     * @param callback   instance of {@link SQCursorCallback}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final String query,
                                                     @Nullable final SQCursorCallback<T> callback) {
        if (validate(ownerClass)) {
            return search(ownerClass, ownerClass.getSimpleName(), query, callback);
        }
        return new ArrayList<>();
    }

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param query      search query
     * @param callback   instance {@link SQCursorCallback}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final String tableName,
                                                     @Nullable final String query,
                                                     @Nullable final SQCursorCallback<T> callback) {
        final List<SQFilter<String>> filters = new ArrayList<>();
        if (SQValidationHelper.emptyValidate(ownerClass, query)) {
            SQAnnotationHelper.annotate(ownerClass, new SQAnnotationClassCallback() {
                @Override
                public void onFoundAnnotation(@NonNull Annotation annotation,
                                              @NonNull Field field)
                        throws IllegalAccessException {
                    final SQType type = SQType.getType(field);
                    if ((type != null) &&
                            ((type == SQType.STRING) || ((type == SQType.VARCHAR)))) {
                        filters.add(new SQFilter<String>(field.getName(), query,
                                SQFilterDelimiter.OR));
                    }
                }
            }, SQField.class);
        }
        return search(ownerClass, tableName, callback, filters.toArray(new SQFilter[0]));
    }

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param callback   instance of the {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return search(ownerClass, callback, filters.toArray(new SQFilter[0]));
        }
        return search(ownerClass, callback);

    }

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param callback   instance of the {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final SQFilter... filters) {
        if (ownerClass != null) {
            return search(ownerClass, ownerClass.getSimpleName(), callback, filters);
        }
        return new ArrayList<>();
    }

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final String tableName,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return search(ownerClass, tableName, callback, filters.toArray(new SQFilter[0]));
        }
        return search(ownerClass, tableName, callback);
    }

    /**
     * Method which provide the searching functional
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return searching result
     */
    public static <T extends SQModel> List<T> search(@Nullable final Class ownerClass,
                                                     @Nullable final String tableName,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final SQFilter... filters) {
        return select(true, ownerClass, tableName, callback, filters);
    }

    //==============================================================================================
    //                                      SELECT
    //==============================================================================================

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass instance of {@link Class}
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    {@link List} of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    public static <T extends SQModel> List<T> select(@Nullable final Class ownerClass,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return select(ownerClass, callback, filters.toArray(new SQFilter[0]));
        }
        return select(ownerClass, callback);
    }

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass instance of {@link Class}
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    public static <T extends SQModel> List<T> select(@Nullable final Class ownerClass,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final SQFilter... filters) {
        if (ownerClass != null) {
            return select(ownerClass, ownerClass.getSimpleName(), callback, filters);
        }
        return new ArrayList<>();
    }

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    public static <T extends SQModel> List<T> select(@Nullable final Class ownerClass,
                                                     @Nullable final String tableName,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return select(ownerClass, tableName, callback, filters.toArray(new SQFilter[0]));
        }
        return select(ownerClass, tableName, callback);
    }

    /**
     * Method which provide the select all functional
     *
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    public static <T extends SQModel> List<T> select(@Nullable final Class ownerClass,
                                                     @Nullable final String tableName,
                                                     @Nullable final SQCursorCallback<T> callback,
                                                     @Nullable final SQFilter... filters) {
        return select(false, ownerClass, tableName, callback, filters);
    }

    /**
     * Method which provide the select all functional
     *
     * @param isSearch   if it search
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of the {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    protected static <T extends SQModel> List<T> select(boolean isSearch,
                                                        @Nullable final Class ownerClass,
                                                        @Nullable final String tableName,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final List<SQFilter> filters) {
        if (validate(filters)) {
            return select(isSearch, ownerClass, tableName, callback, filters.toArray(new SQFilter[0]));

        }
        return select(isSearch, ownerClass, tableName, callback);
    }

    /**
     * Method which provide the select all functional
     *
     * @param isSearch   if it search
     * @param ownerClass instance of {@link Class}
     * @param tableName  {@link String} value of table name
     * @param callback   instance of the {@link SQCursorCallback}
     * @param filters    array of the {@link SQFilter}
     * @param <T>        class type
     * @return selecting result
     */
    protected static <T extends SQModel> List<T> select(boolean isSearch,
                                                        @Nullable final Class ownerClass,
                                                        @Nullable final String tableName,
                                                        @Nullable final SQCursorCallback<T> callback,
                                                        @Nullable final SQFilter... filters) {
        final String methodName = "List<Cursor> select(tableName, ownerClass)";
        List<T> result = new ArrayList<>();
        try {
            final String[] projection = SQModelHelper.generateProjection(getContext(), ownerClass);
            final String filter = getFilter(filters);
            final String[] args = getFilterArgs(isSearch, filters);
            log(null, methodName, null, "[DB] SELECT " + filter);
            log(null, methodName, null, "[DB] SELECT ARGS " + Arrays.deepToString(args));
            final SQLiteDatabase database = getDatabase(SQDatabaseType.READ);
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
            close(cursor);
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
        if (validate(filters)) {
            int length = filters.length;
            for (int i = 0; i < length; i++) {
                final SQFilter filter = filters[i];
                if (validate(filter)) {
                    final String filterValue = filter.getFilter();
                    final String delimiter = filter.getDelimiterValue();
                    final SQFilterDelimiter type = filter.getDelimiter();
                    if (validate(filterValue, delimiter, type)) {
                        boolean isNotLast = i < (length - 1);
                        result.append(filterValue);
                        if ((isNotLast) ||
                                ((!isNotLast) && (type == SQFilterDelimiter.RIGHT_GROUP))) {
                            result.append(delimiter);
                        }
                    }
                }
            }
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
        return getFilterArgs(false, filters);
    }

    /**
     * Method which provide the getting of the filter query
     *
     * @param isSearch if it search
     * @param filters  filter objects
     * @param <T>      objects type
     * @return filter query
     */
    @NonNull
    protected static <T> String[] getFilterArgs(boolean isSearch,
                                                @Nullable final SQFilter<T>... filters) {
        List<String> queryList = new ArrayList<>();
        if (validate(filters)) {
            for (final SQFilter filter : filters) {
                if (validate(filter)) {
                    final String filterValue = (isSearch == true)
                            ? filter.getSearchFilterArgs() : filter.getFilterArgs();
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
            instance.checkLock();
            synchronized (instance) {
                if (type == SQDatabaseType.WRITE) {
                    lockDatabase();
                }
                return instance.openHelper.getDatabase(type);
            }
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
        synchronized (instance) {
            final String methodName = "Context getContext()";
            try {
                return instance.openHelper.getContext();
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
            return null;
        }
    }

    //==============================================================================================
    //                               CURSOR METHODS
    //==============================================================================================

    /**
     * Method which provide the close {@link SQLiteDatabase}
     *
     * @param cursor instance of {@link SQLiteDatabase}
     */
    protected static void close(@Nullable final Cursor cursor) {
        final String methodName = "void close(SQLiteDatabase)";
        try {
            cursor.close();
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
    }

    //==============================================================================================
    //                              LOCK/UNLOCK DATABASE
    //==============================================================================================

    /**
     * Method which provide the releasing of the {@link SQLiteDatabase}
     */
    protected synchronized static void releaseDatabase() {
        final String methodName = "void releaseDatabase()";
        if (instance != null) {
            instance.isOpenWriting = new AtomicBoolean(false);
            log(null, methodName, null, "[DB]: Database released");
        }
    }

    /**
     * Method which provide the locking of the {@link SQLiteDatabase}
     */
    protected synchronized static void lockDatabase() {
        final String methodName = "void lockDatabase()";
        if (instance != null) {
            instance.isOpenWriting = new AtomicBoolean(true);
            log(null, methodName, null, "[DB]: Database locked");
        }
    }

    /**
     * Method which provide the check locking of the {@link SQLiteDatabase}
     */
    protected static void checkLock() {
        final String methodName = " void checkLock()";
        if (instance != null) {
            while (instance.isOpenWriting.equals(true)) {
                try {
                    log(null, methodName, null, "[DB]: Database is locking");
                    Thread.sleep(200);
                } catch (Exception ex) {
                    log(null, methodName, ex, null);
                    return;
                }
            }
        }
    }

}
