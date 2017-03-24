package com.artlite.sqlib.constants;

import android.support.annotation.NonNull;

/**
 * Class which provide equaling functional for {@link com.artlite.sqlib.model.SQFilter}
 */

public enum SQFilterCompare {
    LIKE("LIKE"),
    MORE(">"),
    MORE_EQUAL(">="),
    LESS("<"),
    LESS_EQUAL("<=");

    private final String value;

    /**
     * Constructor which provide the creating {@link SQFilterCompare} from {@link String} value
     *
     * @param value {@link String} value for the {@link SQFilterCompare}
     */
    SQFilterCompare(@NonNull final String value) {
        this.value = value;
    }

    /**
     * Method which provide the getting {@link String} vale for the {@link SQFilterCompare}
     *
     * @return {@link String} value for the {@link SQFilterCompare}
     */
    public String getValue() {
        return value;
    }
}
