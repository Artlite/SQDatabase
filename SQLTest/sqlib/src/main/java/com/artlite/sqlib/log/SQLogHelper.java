package com.artlite.sqlib.log;

import android.support.annotation.Nullable;
import android.util.Log;

import com.artlite.sqlib.helpers.validation.SQValidationHelper;

/**
 * Created by dlernatovich on 12/21/2016.
 */

public final class SQLogHelper {
    /**
     * Method which provide the show log for loggable object
     *
     * @param owner      owner object
     * @param methodName method name
     * @param error      error
     * @param object     object for show
     */
    public static void log(@Nullable final Object owner,
                           @Nullable final String methodName,
                           @Nullable final Exception error,
                           @Nullable final Object object) {
        final StringBuilder builder = new StringBuilder();
        String TAG = SQLoggableObject.class.getSimpleName();
        if (validate(owner)) {
            TAG = owner.getClass().getSimpleName();
            builder.append("Class: \t").append(TAG);
        }
        if (validate(methodName)) {
            builder.append("\nMethod:\t").append(methodName);
        }
        if (validate(error)) {
            builder.append("\nError: \t").append(error.toString());
        }
        if (validate(object)) {
            builder.append("\nObject:\t").append(object.toString());
        }
        if (error == null) {
            Log.e(TAG, builder.toString());
        } else {
            Log.i(TAG, builder.toString());
        }
    }

    /**
     * Method which provide the validation
     *
     * @param objects objects
     * @return validation
     */
    private static boolean validate(@Nullable final Object... objects) {
        return SQValidationHelper.emptyValidate(objects);
    }
}
