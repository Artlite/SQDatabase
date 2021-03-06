package com.artlite.sqlib.constants;

import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 2/10/2017.
 */

public enum SQFilterDelimiter {
    AND(" AND "),
    OR(" OR "),
    AND_LEFT_GROUP(" AND ( "),
    AND_RIGHT_GROUP(" ) AND "),
    OR_LEFT_GROUP(" OR ( "),
    OR_RIGHT_GROUP(" ) OR "),
    RIGHT_GROUP(" ) "),
    LEFT_GROUP(" ( ");
    private String value;

    /**
     * Constructor which provide the create the {@link SQFilterDelimiter} from {@link String} value
     *
     * @param value join value
     */
    SQFilterDelimiter(@NonNull final String value) {
        this.value = value;
    }

    /**
     * Method which provide the value getting
     *
     * @return value
     */
    @NonNull
    public String getValue() {
        return value;
    }
}
