package com.artlite.sqlib.log;

import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.validation.SQValidationHelper;

/**
 * Created by dlernatovich on 12/21/2016.
 */

public abstract class SQLoggableObject {

    /**
     * Method which provide the show log for loggable object
     *
     * @param owner      owner object
     * @param methodName method name
     * @param error      error
     * @param object     object for show
     * @param <T>
     */
    protected static <T extends SQLoggableObject> void log(@Nullable final T owner,
                                                           @Nullable final String methodName,
                                                           @Nullable final Exception error,
                                                           @Nullable final Object object) {
        SQLogHelper.log(owner, methodName, error, object);
    }

    /**
     * Method which provide the show log for loggable object
     *
     * @param methodName method name
     * @param error      error
     * @param object     object for show
     * @param <T>
     */
    protected <T extends SQLoggableObject> void log(@Nullable final String methodName,
                                                    @Nullable final Exception error,
                                                    @Nullable final Object object) {
        log(this, methodName, error, object);
    }

    /**
     * Method which provide the validation
     *
     * @param objects objects
     * @return validation
     */
    protected static boolean validate(@Nullable final Object... objects) {
        return SQValidationHelper.emptyValidate(objects);
    }
}
