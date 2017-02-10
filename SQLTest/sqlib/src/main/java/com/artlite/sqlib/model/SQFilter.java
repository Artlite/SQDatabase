package com.artlite.sqlib.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.SQFilterDelimiter;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;

/**
 * Class which provide the filtering values
 * Created by Artli on 24.12.2016.
 */

public class SQFilter<K extends Object> {
    /**
     * Fields
     */
    private final String fieldName;
    private final K value;
    private final SQFilterDelimiter delimiter;

    /**
     * Constructor which provide the creating of the filter
     *
     * @param fieldName field name
     * @param value     search value
     */
    public SQFilter(@Nullable final String fieldName,
                    @Nullable final K value) {
        this(fieldName, value, SQFilterDelimiter.AND);
    }

    /**
     * Constructor which provide the creating of the filter
     *
     * @param fieldName field name
     * @param value     search value
     */
    public SQFilter(@Nullable final String fieldName,
                    @Nullable final K value,
                    @NonNull final SQFilterDelimiter delimiter) {
        this.fieldName = fieldName;
        this.value = value;
        this.delimiter = delimiter;
    }

    /**
     * Method which provide the getting of the filter query
     *
     * @return filter query
     */
    @Nullable
    public final String getFilter() {
        if (SQValidationHelper.nullValidate(fieldName)) {
            return String.format("%s %s", fieldName, "LIKE ?");
        }
        return null;
    }

    /**
     * Method which provide the getting of the filter delimiter
     *
     * @return filter delimiter value
     */
    @NonNull
    public String getDelimiter() {
        return delimiter.getValue();
    }

    /**
     * Method which provide the getting of the filter query args
     *
     * @return filter query
     */
    @Nullable
    public final String getFilterArgs() {
        if (value.getClass().getSimpleName().equalsIgnoreCase("boolean")) {
            return (((Boolean) value) == true) ? "1" : "0";
        } else if (SQValidationHelper.emptyValidate(value)) {
            return String.valueOf(value);
        }
        return null;
    }
}
