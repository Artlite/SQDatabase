package com.artlite.sqlib.helpers.model;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.callbacks.SQAnnotationCallback;
import com.artlite.sqlib.callbacks.SQAnnotationClassCallback;
import com.artlite.sqlib.helpers.annotation.SQAnnotationHelper;
import com.artlite.sqlib.model.SQModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artli on 23.12.2016.
 */

abstract class SQModelHelper_Projection extends SQModelHelper_Convert {
    /**
     * Method which provide the getting of the projection from the {@link SQModel} object
     *
     * @param objectClass instance of {@link SQModel} class
     * @param <T>         {@link SQModel} type
     * @return generated projection
     */
    public static <T extends SQModel> String[] generateProjection(@Nullable final Class objectClass) {
        final List<String> projection = new ArrayList<>();
        projection.add(BaseColumns._ID);
        SQAnnotationHelper.annotate(objectClass, new SQAnnotationClassCallback() {
            @Override
            public void onFoundAnnotation(@NonNull final Annotation annotation,
                                          @NonNull final Field field)
                    throws IllegalAccessException {
                final String name = field.getName();
                projection.add(name);
            }
        }, SQField.class);
        return projection.toArray(new String[projection.size()]);
    }
}
