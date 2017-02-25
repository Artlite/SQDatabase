package com.artlite.sqlib.helpers.model;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.callbacks.SQAnnotationCallback;
import com.artlite.sqlib.constants.SQType;
import com.artlite.sqlib.helpers.preference.SQPreferenceHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.helpers.annotation.SQAnnotationHelper;
import com.artlite.sqlib.model.SQModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class which provide the helper functional for {@link com.artlite.sqlib.model.SQModel}
 * Created by dlernatovich on 12/22/2016.
 */

public final class SQModelHelper extends SQModelHelper_Cursor {

    private static final String K_SQL_CREATE_TABLE = "create table if not exists %s (%s)";
    private static final String K_TYPE_ID = "integer primary key autoincrement";

    /**
     * Method which provide the getting of the init tables queries
     *
     * @param objects objects
     * @param <T>     objects type
     * @return list of queries
     */
    @NonNull
    public static <T extends SQModel> List<String> getCreateQueries(@Nullable final Context context,
                                                                    @Nullable final T... objects) {
        final List<String> sql = new ArrayList<>();
        if (SQValidationHelper.emptyValidate(objects)) {
            for (final T model : objects) {
                final String query = getCreateQuery(context, model);
                if (!TextUtils.isEmpty(query)) {
                    sql.add(query);
                }
            }
        }
        return sql;
    }

    /**
     * Method which provide the getting of the init table query for object
     *
     * @param object object
     * @param <T>    object type
     * @return query type
     */
    @NonNull
    public static <T extends SQModel> String getCreateQuery(@Nullable final Context context,
                                                            @Nullable final T object) {
        //Fields
        final StringBuilder result = new StringBuilder();
        final List<String> fields = new ArrayList<>();
        final String tableName = (object != null) ? object.table() : null;
        final String tableNamePreferences = String.format("%s_create_fields", tableName);
        String fieldsValue = null;
        //Get SharedPreferences fields
        fields.addAll(SQPreferenceHelper.getList(context, tableNamePreferences));
        //Get fields
        if (SQValidationHelper.emptyValidate(object) && (fields.size() == 0)) {
            fields.add(String.format("%s %s", BaseColumns._ID, K_TYPE_ID));
            SQAnnotationHelper.annotate(object, new SQAnnotationCallback<T>() {
                @Override
                public void onFoundAnnotation(@NonNull final T object,
                                              @NonNull final Annotation annotation,
                                              @NonNull final Field field)
                        throws IllegalAccessException {
                    final String name = field.getName();
                    final String type = SQType.getTypeName(field);
                    fields.add(String.format("%s %s", name, type));
                }
            }, SQField.class);
            //Save SharedPreferences fields
            SQPreferenceHelper.save(context, fields, tableNamePreferences);
        }
        //Get fields string
        fieldsValue = TextUtils.join(", ", fields);
        //Add query
        if (SQValidationHelper.emptyValidate(fieldsValue, tableName)) {
            result.append(String.format(K_SQL_CREATE_TABLE, tableName, fieldsValue));
        }
        return result.toString();
    }

    /**
     * Method which provide the getting of the list of the {@link ContentValues}
     *
     * @param objects list of {@link SQModel}
     * @param <T>     objects type
     * @return list of {@link ContentValues}
     */
    @NonNull
    public static <T extends SQModel> List<ContentValues> getContentValues(@Nullable final T... objects) {
        List<ContentValues> values = new ArrayList<>();
        if (SQValidationHelper.emptyValidate(objects)) {
            for (final T model : objects) {
                final ContentValues value = getContentValue(model);
                if (value != null) {
                    values.add(value);
                }
            }
        }
        return values;
    }

    /**
     * Method which provide the getting of the {@link ContentValues} from {@link SQModel}
     *
     * @param object instance of {@link SQModel}
     * @param <T>    object type
     * @return instance of the {@link ContentValues}
     */
    @Nullable
    public static <T extends SQModel> ContentValues getContentValue(@Nullable final T object) {
        if (SQValidationHelper.emptyValidate(object)) {
            final ContentValues contentValue = new ContentValues();
            SQAnnotationHelper.annotate(object, new SQAnnotationCallback<T>() {
                @Override
                public void onFoundAnnotation(@NonNull final T object,
                                              @NonNull final Annotation annotation,
                                              @NonNull final Field field)
                        throws IllegalAccessException {
                    putValue(object, contentValue, field);
                }
            }, SQField.class);
            return contentValue;
        }
        return null;
    }

    /**
     * Method which provide the put value to {@link ContentValues}
     *
     * @param contentValues instance of {@link ContentValues}
     * @param field         instance of {@link Field}
     */
    protected static void putValue(@Nullable final Object owner,
                                   @Nullable final ContentValues contentValues,
                                   @Nullable final Field field) {
        if (SQValidationHelper.emptyValidate(contentValues, field)) {
            final String name = field.getName();
            final SQType type = SQType.getType(field);
            if (validate(name, type)) {
                switch (type) {
                    case INTEGER:
                        final Object intObject = SQAnnotationHelper.getFieldValue(owner, field, Integer.class);
                        if (intObject != null) {
                            contentValues.put(name, (Integer) intObject);
                        }
                        break;
                    case BLOB:
                        final Object blobObject = SQAnnotationHelper.getFieldValue(owner, field, Parcelable.class);
                        if ((blobObject != null) && (blobObject instanceof Parcelable)) {
                            final byte[] bytes = convert((Parcelable) blobObject);
                            contentValues.put(name, bytes);
                        }
                        break;
                    case BOOLEAN:
                        final Object booleanObject = SQAnnotationHelper.getFieldValue(owner, field, Boolean.class);
                        if (booleanObject != null) {
                            contentValues.put(name, (Boolean) booleanObject);
                        }
                        break;
                    case DATE:
                        final Object dateObject = SQAnnotationHelper.getFieldValue(owner, field, Date.class);
                        if (dateObject != null) {
                            final String dateString = convert((Date) dateObject);
                            if (dateString != null) {
                                contentValues.put(name, dateString);
                            }
                        }
                        break;
                    case DOUBLE:
                    case FLOAT:
                        final Object floatObject = SQAnnotationHelper.getFieldValue(owner, field, Float.class);
                        if (floatObject != null) {
                            contentValues.put(name, (Float) floatObject);
                        } else {
                            final Object doubleValue = SQAnnotationHelper.getFieldValue(owner, field, Double.class);
                            if (doubleValue != null) {
                                contentValues.put(name, (Double) doubleValue);
                            }
                        }
                        break;
                    case VARCHAR:
                    case STRING:
                        final Object stringObject = SQAnnotationHelper.getFieldValue(owner, field, String.class);
                        if (stringObject != null) {
                            contentValues.put(name, (String) stringObject);
                        }
                        break;
                }
            }
        }
    }

}
