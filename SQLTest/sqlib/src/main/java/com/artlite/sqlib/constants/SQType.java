package com.artlite.sqlib.constants;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.validation.SQValidationHelper;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * {@link com.artlite.sqlib.model.SQModel} field type
 * Created by dlernatovich on 12/21/2016.
 */

public enum SQType {
    BLOB("BLOB"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    FLOAT("DECIMAL"),
    DOUBLE("DOUBLE"),
    INTEGER("INTEGER"),
    VARCHAR("VARCHAR"),
    LONG("LONG"),
    STRING("VARCHAR");
    /**
     * Type name
     */
    private final String name;

    /**
     * Method which provide the init {@link SQType} from name
     *
     * @param name {@link SQType} name value
     */
    SQType(@NonNull final String name) {
        this.name = name;
    }

    /**
     * Method which provide the getting of name of {@link SQType}
     *
     * @return instance of {@link SQType} name
     */
    public String getName() {
        return name;
    }

    /**
     * Method which provide the getting of the {@link SQType} from {@link Field}
     *
     * @param field instance of {@link Field}
     * @return instance of {@link SQType}
     */
    @Nullable
    public static SQType getType(@Nullable final Field field) {
        if (SQValidationHelper.emptyValidate(field)) {
            final Class type = field.getType();
            if (type.getSimpleName().equalsIgnoreCase("double")) {
                return DOUBLE;
            } else if (type.getSimpleName().equalsIgnoreCase("float")) {
                return FLOAT;
            } else if (type.getSimpleName().equalsIgnoreCase("int")) {
                return INTEGER;
            } else if (type.getSimpleName().equalsIgnoreCase("boolean")) {
                return BOOLEAN;
            } else if (type.getSimpleName().equalsIgnoreCase("long")) {
                return LONG;
            } else if (type == String.class) {
                return STRING;
            } else if (type == Float.class) {
                return FLOAT;
            } else if (type == Double.class) {
                return DOUBLE;
            } else if (type == Boolean.class) {
                return BOOLEAN;
            } else if (type == Integer.class) {
                return INTEGER;
            } else if (type == Long.class) {
                return LONG;
            } else if (type == Date.class) {
                return DATE;
            } else {
                return BLOB;
            }
        }
        return null;
    }

    /**
     * Method which provide the getting of the {@link Field} name
     *
     * @param field instance of {@link Field}
     * @return type name of the {@link Field}
     */
    @Nullable
    public static String getTypeName(@Nullable final Field field) {
        if (getType(field) != null) {
            return getType(field).getName();
        }
        return BLOB.getName();
    }
}
