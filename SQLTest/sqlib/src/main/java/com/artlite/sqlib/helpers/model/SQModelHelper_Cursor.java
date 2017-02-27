package com.artlite.sqlib.helpers.model;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.parcelable.SQParcelableHelper;

import java.util.Date;

/**
 * Created by Artli on 23.12.2016.
 */

abstract class SQModelHelper_Cursor extends SQModelHelper_Projection {

    /**
     * Method which provide the getting {@link String} from {@link Cursor}
     *
     * @param cursor instance of {@link Cursor}
     * @return value of {@link String}
     */
    @NonNull
    public static int getID(@Nullable final Cursor cursor) {
        final String methodName = "String getString(cursor, fieldName)";
        int result = Integer.MIN_VALUE;
        try {
            result = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting {@link String} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link String}
     */
    @NonNull
    public static String getString(@Nullable final Cursor cursor,
                                   @Nullable final String fieldName) {
        final String methodName = "String getString(cursor, fieldName)";
        final StringBuilder result = new StringBuilder();
        try {
            result.append(cursor.getString(cursor.getColumnIndexOrThrow(fieldName)));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result.toString().trim();
    }

    /**
     * Method which provide the getting {@link Date} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link Date}
     */
    @NonNull
    public static Date getDate(@Nullable final Cursor cursor,
                               @Nullable final String fieldName) {
        final String methodName = "String getDate(cursor, fieldName)";
        Date date = new Date();
        try {
            final String dateString = cursor.getString(cursor.getColumnIndexOrThrow(fieldName));
            date = convert(dateString);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return date;
    }

    /**
     * Method which provide the getting {@link Double} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link Double}
     */
    public static double getDouble(@Nullable final Cursor cursor,
                                   @Nullable final String fieldName) {
        final String methodName = "String getDate(cursor, fieldName)";
        double result = 0;
        try {
            result = cursor.getDouble(cursor.getColumnIndexOrThrow(fieldName));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting {@link Float} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link Float}
     */
    public static float getFloat(@Nullable final Cursor cursor,
                                 @Nullable final String fieldName) {
        final String methodName = "String getDate(cursor, fieldName)";
        float result = 0;
        try {
            result = cursor.getFloat(cursor.getColumnIndexOrThrow(fieldName));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting {@link Integer} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link Integer}
     */
    public static int getInteger(@Nullable final Cursor cursor,
                                 @Nullable final String fieldName) {
        final String methodName = "String getDate(cursor, fieldName)";
        int result = 0;
        try {
            result = cursor.getInt(cursor.getColumnIndexOrThrow(fieldName));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting {@link Boolean} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name from cursor
     * @return value of {@link Boolean}
     */
    public static boolean getBoolean(@Nullable final Cursor cursor,
                                     @Nullable final String fieldName) {
        final String methodName = "String getDate(cursor, fieldName)";
        boolean result = false;
        try {
            result = cursor.getInt(cursor.getColumnIndexOrThrow(fieldName)) > 0;
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting {@link Parcelable} object from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param creator   object {@link Parcelable.Creator}
     * @param fieldName field name
     * @param <T>       object type
     * @return instance of getting object
     */
    @Nullable
    public static <T extends Parcelable> T getObject(@Nullable final Cursor cursor,
                                                     @Nullable final Parcelable.Creator<T> creator,
                                                     @Nullable final String fieldName) {
        final String methodName = "T getObject(cursor, creator, fieldName)";
        try {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow(fieldName));
            Object object = SQParcelableHelper.convert(bytes, creator);
            return (T) object;
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return null;
    }

    /**
     * Method which provide the getting of the {@link Bitmap} from {@link Cursor}
     *
     * @param cursor    instance of {@link Cursor}
     * @param fieldName field name
     * @return instance of {@link Bitmap}
     */
    @Nullable
    public static Bitmap getBitmap(@Nullable final Cursor cursor,
                                   @Nullable final String fieldName) {
        final String methodName = "Bitmap getBitmap(cursor, fieldName)";
        try {
            byte[] bytes = cursor.getBlob(cursor.getColumnIndexOrThrow(fieldName));
            Bitmap object = SQParcelableHelper.convertToBitmap(bytes);
            return object;
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return null;
    }

}
