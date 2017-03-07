package com.artlite.sqlib.helpers.abs;

import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.log.SQLoggableObject;

/**
 * Created by dlernatovich on 12/14/2016.
 */

public abstract class SQBaseHelper extends SQLoggableObject {

    /**
     * Method which provide the empty validations
     *
     * @param objects objects for validate
     * @return validation results
     */
    public static boolean isEmpty(@Nullable final Object... objects) {
        return SQValidationHelper.isEmpty(objects);
    }

    /**
     * Method which provide the validation for null elements
     *
     * @param objects objects for validate
     * @return validation result
     */
    public static boolean isNull(@Nullable final Object... objects) {
        return SQValidationHelper.isNull(objects);
    }

}
