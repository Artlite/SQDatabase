package com.artlite.sqlib.helpers.model;

import android.content.Context;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.callbacks.SQAnnotationClassCallback;
import com.artlite.sqlib.helpers.annotation.SQAnnotationHelper;
import com.artlite.sqlib.helpers.preference.SQPreferenceHelper;
import com.artlite.sqlib.model.SQModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which provide the projection functional
 * Created by Artli on 23.12.2016.
 */

abstract class SQModelHelper_Projection extends SQModelHelper_Convert {

    /**
     * Method which provide the getting of the projection from the {@link SQModel} object
     *
     * @param context     instance of {@link Context}
     * @param objectClass instance of {@link SQModel} class
     * @param <T>         {@link SQModel} type
     * @return generated projection
     */
    public static <T extends SQModel> String[] generateProjection(@Nullable final Context context,
                                                                  @Nullable final Class objectClass) {
        final List<String> projection = new ArrayList<>();
        //Validation
        if (validate(context, objectClass)) {
            //SharedPreferences
            final String className = String.format("%s_projection", objectClass.getName());
            projection.addAll(SQPreferenceHelper.getList(context, className));
            //Preferences validation
            if (projection.size() == 0) {
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
                //Save SharedPreferences
                SQPreferenceHelper.save(context, projection, className);
            }
        }
        return projection.toArray(new String[projection.size()]);
    }

    /**
     * Method which provide the getting of the projection from the {@link SQModel} object
     *
     * @param context     instance of {@link Context}
     * @param objectClass instance of {@link SQModel} class
     * @return generated projection
     */
    public static void clearProjection(@Nullable final Context context,
                                       @Nullable final Class objectClass) {
        final List<String> projection = new ArrayList<>();
        //Validation
        if (validate(context, objectClass)) {
            //SharedPreferences
            final String className = String.format("%s_projection", objectClass.getName());
            SQPreferenceHelper.delete(context, className);
        }
    }

}
