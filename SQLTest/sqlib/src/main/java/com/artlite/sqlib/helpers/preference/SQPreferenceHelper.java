package com.artlite.sqlib.helpers.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.abs.SQBaseHelper;
import com.artlite.sqlib.helpers.convert.SQConvertHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Artli on 30.12.2016.
 */

public final class SQPreferenceHelper extends SQBaseHelper {

    /**
     * Constants
     */
    protected static final String K_SHARED_PREFERENCE_NAME = "SQ_SHARED_PREFERENCES";

    /**
     * Method which provide the saving of the String value to the SharedPreferences
     *
     * @param context current context
     * @param object  current String value
     * @param id      SharedPreferences key
     */
    public static boolean save(@Nullable final Context context,
                               @Nullable final String object,
                               @Nullable final String id) {
        final String methodName = "boolean save(context, object, id)";
        if (validate(object, context, id)) {
            try {
                SharedPreferences.Editor editor = getEditor(context);
                editor.putString(id, object);
                editor.commit();
                return true;
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return false;
    }

    /**
     * Method which provide the saving of the {@link Boolean} value to the SharedPreferences
     *
     * @param context current context
     * @param object  current {@link Boolean} value
     * @param id      SharedPreferences key
     */
    public static boolean save(@Nullable final Context context,
                               @Nullable final boolean object,
                               @Nullable final String id) {
        final String methodName = "boolean save(context, object, id)";
        if (validate(object, context, id)) {
            try {
                SharedPreferences.Editor editor = getEditor(context);
                editor.putBoolean(id, object);
                editor.commit();
                return true;
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return false;
    }

    /**
     * Method which provide the saving of the {@link List} value to the SharedPreferences
     *
     * @param context current context
     * @param objects current list of {@link List} values
     * @param id      SharedPreferences key
     */
    public static boolean save(@Nullable final Context context,
                               @Nullable final List<String> objects,
                               @Nullable final String id) {
        final String methodName = "boolean save(context, object, id)";
        if (validate(objects, context, id)) {
            try {
                SharedPreferences.Editor editor = getEditor(context);
                editor.putStringSet(id, new HashSet<String>(objects));
                editor.commit();
                return true;
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return false;
    }

    /**
     * Method which provide the saving of the {@link Date} value to the SharedPreferences
     *
     * @param context current context
     * @param date    current list of {@link Date} values
     * @param id      SharedPreferences key
     */
    public static boolean save(@Nullable final Context context,
                               @Nullable final Date date,
                               @Nullable final String id) {
        final String methodName = "boolean save(context, object, id)";
        if (validate(date, context, id)) {
            try {
                final String dateString = convert(date);
                SharedPreferences.Editor editor = getEditor(context);
                editor.putString(id, dateString);
                editor.commit();
                return true;
            } catch (Exception ex) {
                log(null, methodName, ex, null);
            }
        }
        return false;
    }

    /**
     * Method which provide the getting of the the String value by key
     *
     * @param context current context
     * @param id      key ID
     * @return current String
     */
    @NonNull
    public static String getString(@Nullable final Context context,
                                   @Nullable final String id) {
        final String methodName = "String get(context, id)";
        final StringBuilder result = new StringBuilder("");
        try {
            SharedPreferences settings = getPreferences(context);
            result.append(settings.getString(id, null));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result.toString();
    }

    /**
     * Method which provide the getting of the the {@link Boolean} value by key
     *
     * @param context current context
     * @param id      key ID
     * @return current {@link Boolean}
     */
    public static boolean getBoolean(@Nullable final Context context,
                                     @Nullable final String id) {
        final String methodName = "String get(context, id)";
        try {
            SharedPreferences settings = getPreferences(context);
            return settings.getBoolean(id, false);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return false;
    }

    /**
     * Method which provide the getting of the the {@link Date} value by key
     *
     * @param context current context
     * @param id      key ID
     * @return current {@link Date}
     */
    @NonNull
    public static Date getDate(@Nullable final Context context,
                               @Nullable final String id) {
        final String methodName = "String get(context, id)";
        try {
            final SharedPreferences settings = getPreferences(context);
            return convert(settings.getString(id, null));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return new Date();
    }

    /**
     * Method which provide the getting of the the {@link List} value by key
     *
     * @param context current context
     * @param id      key ID
     * @return current {@link List}
     */
    @NonNull
    public static List<String> getList(@Nullable final Context context,
                                       @Nullable final String id) {
        final String methodName = "List<String> get(context, id)";
        final List<String> result = new ArrayList<>();
        try {
            SharedPreferences settings = getPreferences(context);
            result.addAll(settings.getStringSet(id, Collections.<String>emptySet()));
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return result;
    }

    /**
     * Method which provide the getting of the {@link SharedPreferences}
     *
     * @param context instance of {@link Context}
     * @return instance of {@link SharedPreferences}
     */
    @Nullable
    protected static SharedPreferences getPreferences(@Nullable final Context context) {
        final String methodName = "SharedPreferences getPreferences(context)";
        try {
            return context.getSharedPreferences(K_SHARED_PREFERENCE_NAME, 0);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return null;
    }

    /**
     * Method which provide the getting of the {@link android.content.SharedPreferences.Editor}
     *
     * @param context instance of the {@link Context}
     * @return instance of the {@link android.content.SharedPreferences.Editor}
     */
    @Nullable
    protected static SharedPreferences.Editor getEditor(@Nullable final Context context) {
        final String methodName = "SharedPreferences.Editor getEditor(context)";
        try {
            return getPreferences(context).edit();
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
        return null;
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    public static String convert(@Nullable final Date date) {
        return SQConvertHelper.convert(date);
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    public static Date convert(@Nullable final String date) {
        return SQConvertHelper.convert(date);
    }

}
