package com.artlite.sqlib.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.SQFilterCompare;
import com.artlite.sqlib.constants.SQFilterDelimiter;
import com.artlite.sqlib.helpers.convert.SQConvertHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;

import java.util.Date;

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
    private SQFilterDelimiter delimiter;
    private SQFilterCompare compare;

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
     * @param delimiter instance of {@link SQFilterDelimiter}
     */
    public SQFilter(@Nullable final String fieldName,
                    @Nullable final K value,
                    @NonNull final SQFilterDelimiter delimiter) {
        this(fieldName, value, delimiter, SQFilterCompare.LIKE);
    }

    /**
     * Constructor which provide the creating of the filter
     *
     * @param fieldName field name
     * @param value     search value
     * @param compare   instance of {@link SQFilterCompare}
     */
    public SQFilter(@Nullable final String fieldName,
                    @Nullable final K value,
                    @NonNull final SQFilterCompare compare) {
        this(fieldName, value, SQFilterDelimiter.AND, compare);
    }

    /**
     * Constructor which provide the creating of the filter
     *
     * @param fieldName field name
     * @param value     search value
     * @param delimiter instance of {@link SQFilterDelimiter}
     * @param compare   instance of {@link SQFilterCompare}
     */
    public SQFilter(@Nullable final String fieldName,
                    @Nullable final K value,
                    @NonNull final SQFilterDelimiter delimiter,
                    @NonNull final SQFilterCompare compare) {
        this.fieldName = fieldName;
        this.value = value;
        this.delimiter = delimiter;
        this.compare = compare;
    }

    /**
     * Method which provide the getting of the filter query
     *
     * @return filter query
     */
    @Nullable
    public final String getFilter() {
        if (SQValidationHelper.nullValidate(fieldName)) {
            return String.format("%s %s %s", fieldName, getCompareValue(), " ?");
        }
        return null;
    }

    /**
     * Method which provide the getting of the {@link SQFilterDelimiter} for the {@link SQFilter}
     *
     * @return instance of {@link SQFilterDelimiter}
     */
    public SQFilterDelimiter getDelimiter() {
        if (delimiter == null) {
            delimiter = SQFilterDelimiter.AND;
        }
        return delimiter;
    }

    /**
     * Method which provide the setting of the {@link SQFilterDelimiter} inside the {@link SQFilter}
     *
     * @param delimiter instance of {@link SQFilterDelimiter}
     */
    public void setDelimiter(@Nullable final SQFilterDelimiter delimiter) {
        if (!SQValidationHelper.isEmpty(delimiter)) {
            this.delimiter = delimiter;
        }
    }

    /**
     * Method which provide the getting of the filter delimiter
     *
     * @return filter delimiter value
     */
    @NonNull
    public String getDelimiterValue() {
        return delimiter.getValue();
    }

    /**
     * Method which provide the getting {@link SQFilterCompare} for {@link SQFilter}
     *
     * @return instance of {@link SQFilterCompare}
     */
    @NonNull
    public SQFilterCompare getCompare() {
        if (compare == null) {
            compare = SQFilterCompare.LIKE;
        }
        return compare;
    }

    /**
     * Method which provide the setting of the {@link SQFilterCompare} inside the {@link SQFilter}
     *
     * @param compare instance of {@link SQFilterCompare}
     */
    public void setCompare(@Nullable final SQFilterCompare compare) {
        if (!SQValidationHelper.isEmpty(compare)) {
            this.compare = compare;
        }
    }

    /**
     * Method which provide the getting of the {@link SQFilterCompare} value
     *
     * @return instance of {@link SQFilterCompare} value
     */
    @NonNull
    public String getCompareValue() {
        return getCompare().getValue();
    }

    /**
     * Method which provide the getting of the filter query args
     *
     * @return filter query
     */
    @Nullable
    public final String getSearchFilterArgs() {
        final String args = getFilterArgs().trim();
        if ((args != null) && (!args.isEmpty())) {
            return String.format("%%%s%%", args);
        }
        return null;
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
        } else if (value.getClass() == Date.class) {
            return SQConvertHelper.convert((Date) value);
        } else if (SQValidationHelper.emptyValidate(value)) {
            return String.valueOf(value);
        }
        return null;
    }
}
