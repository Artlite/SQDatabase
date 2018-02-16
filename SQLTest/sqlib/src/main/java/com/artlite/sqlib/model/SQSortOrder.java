package com.artlite.sqlib.model;

import android.support.annotation.NonNull;

/**
 * Class which provide the sort functionality
 * Created by dlernatovich on 2/16/2018.
 */

public final class SQSortOrder {

    public enum Type {
        ASC("ASC"),
        DESC("DESC");

        /**
         * {@link String} value of the value
         */
        protected final String value;

        /**
         * Constructor which provide to create the {@link Type} with parameter
         *
         * @param value {@link String} value of the name
         */
        Type(String value) {
            this.value = value;
        }
    }

    /**
     * {@link String} value of the field name
     */
    private final String fieldName;

    /**
     * Instance of the {@link Type}
     */
    private final Type type;

    /**
     * Constructor which provide the create the {@link SQSortOrder} with parameters
     *
     * @param fieldName {@link String} value of the field name
     * @param type      instance of the {@link Type}
     */
    public SQSortOrder(@NonNull String fieldName, @NonNull Type type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    /**
     * Method which provide the getting of the sort order query
     *
     * @return {@link String} value of the query
     */
    @NonNull
    public final String getSortQuery() {
        return fieldName + " " + type.value;
    }
}
