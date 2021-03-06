package com.artlite.sqlib.callbacks;

import android.support.annotation.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Annotation callback
 */

public interface SQAnnotationClassCallback {
    /**
     * Method which provide the action when one of the annotation class found
     *
     * @param annotation annotation
     * @param field      field with annotation
     */
    void onFoundAnnotation(@NonNull final Annotation annotation,
                           @NonNull final Field field)
            throws IllegalAccessException;
}
