package com.artlite.sqlib.helpers.annotation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.callbacks.SQAnnotationCallback;
import com.artlite.sqlib.callbacks.SQAnnotationClassCallback;
import com.artlite.sqlib.helpers.abs.SQBaseHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.log.SQLogHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Class which provide the annotation performing
 * Created by dlernatovich on 12/14/2016.
 */

public final class SQAnnotationHelper extends SQBaseHelper {
    /**
     * TAG field
     */
    public static final String TAG = SQAnnotationHelper.class.getSimpleName();

    //==============================================================================================
    //                              BASIC ANNOTATION METHODS
    //==============================================================================================

    /**
     * Method which provide the annotation performing
     *
     * @param object  owner object
     * @param classes list of {@link Annotation} classes
     */
    public static <T extends Object> void annotate(@Nullable final T object,
                                                   @Nullable final SQAnnotationCallback<T> callback,
                                                   @Nullable final Class... classes) {
        //Check inner objects
        if (!validate(object, callback, classes)) {
            return;
        }
        //Get class
        final Class oCLass = object.getClass();
        //Get fields
        final Field[] fields = oCLass.getDeclaredFields();
        if (validate(fields)) {
            for (final Field field : fields) {
                for (final Class aClass : classes) {
                    if (validate(aClass)) {
                        final Annotation annotation = field.getAnnotation(aClass);
                        if (validate(annotation)) {
                            execute(object, callback, annotation, field);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method which provide the annotation performing
     *
     * @param oCLass  owner object
     * @param classes list of {@link Annotation} classes
     */
    public static <T extends Object> void annotate(@Nullable final Class oCLass,
                                                   @Nullable final SQAnnotationClassCallback callback,
                                                   @Nullable final Class... classes) {
        //Check inner objects
        if (!validate(oCLass, callback, classes)) {
            return;
        }
        //Get fields
        final Field[] fields = oCLass.getDeclaredFields();
        if (validate(fields)) {
            for (final Field field : fields) {
                for (final Class aClass : classes) {
                    if (validate(aClass)) {
                        final Annotation annotation = field.getAnnotation(aClass);
                        if (validate(annotation)) {
                            execute(callback, annotation, field);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method which provide the performing of callback
     *
     * @param object     object
     * @param callback   callback
     * @param annotation annotation
     */
    private static <T extends Object> void execute(@NonNull final T object,
                                                   @NonNull final SQAnnotationCallback<T> callback,
                                                   @NonNull final Annotation annotation,
                                                   @NonNull final Field field) {
        final String methodName = "void execute(object, callback, annotation, field)";
        try {
            field.setAccessible(true);
            callback.onFoundAnnotation(object, annotation, field);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
    }

    /**
     * Method which provide the performing of callback
     *
     * @param callback   callback
     * @param annotation annotation
     */
    private static <T extends Object> void execute(@NonNull final SQAnnotationClassCallback callback,
                                                   @NonNull final Annotation annotation,
                                                   @NonNull final Field field) {
        final String methodName = "void execute(object, callback, annotation, field)";
        try {
            field.setAccessible(true);
            callback.onFoundAnnotation(annotation, field);
        } catch (Exception ex) {
            log(null, methodName, ex, null);
        }
    }

    /**
     * Method which provide the getting of the {@link Field} value
     *
     * @param owner instance of the {@link Object}
     * @param field instance of {@link Field}
     * @param type  class type
     * @return
     */
    @Nullable
    public static Object getFieldValue(@Nullable final Object owner,
                                       @Nullable final Field field,
                                       @Nullable final Class type) {
        final String methodName = "Object getFieldValue(owner, field, type)";
        Object object = null;
        if (SQValidationHelper.emptyValidate(field, type, owner)) {
            try {
                if ((type == Boolean.class) && isBoolean(owner, field)) {
                    object = new Boolean(field.getBoolean(owner));
                } else if ((type == Integer.class) && isInt(owner, field)) {
                    object = new Integer(field.getInt(owner));
                } else if ((type == Double.class) && isDouble(owner, field)) {
                    object = new Double(field.getDouble(owner));
                } else if ((type == Float.class) && isFloat(owner, field)) {
                    object = new Float(field.getFloat(owner));
                }
            } catch (Exception e) {
                SQLogHelper.log(null, methodName, e, null);
            }
            if ((object == null) && (field.getType() == type)) {
                try {
                    object = field.get(owner);
                } catch (Exception e) {
                    SQLogHelper.log(null, methodName, e, null);
                }
            } else if (object.getClass() != type) {
                object = null;
            }
        }
        return object;
    }

    /**
     * Method which provide the checking if field is double
     *
     * @param owner owner
     * @param field field
     * @return checking valud
     */
    protected static boolean isDouble(@Nullable final Object owner,
                                      @Nullable final Field field) {
        final String methodName = "boolean isDouble(owner, field)";
        try {
            field.getDouble(owner);
            return true;
        } catch (Exception ex) {
            SQLogHelper.log(null, methodName, ex, null);
        }
        return false;
    }

    /**
     * Method which provide the checking if field is double
     *
     * @param owner owner
     * @param field field
     * @return checking valud
     */
    protected static boolean isFloat(@Nullable final Object owner,
                                     @Nullable final Field field) {
        final String methodName = "boolean isFloat(owner, field)";
        try {
            field.getFloat(owner);
            return true;
        } catch (Exception ex) {
            SQLogHelper.log(null, methodName, ex, null);
        }
        return false;
    }

    /**
     * Method which provide the checking if field is double
     *
     * @param owner owner
     * @param field field
     * @return checking valud
     */
    protected static boolean isBoolean(@Nullable final Object owner,
                                       @Nullable final Field field) {
        final String methodName = "boolean isBoolean(owner, field)";
        try {
            field.getBoolean(owner);
            return true;
        } catch (Exception ex) {
            SQLogHelper.log(null, methodName, ex, null);
        }
        return false;
    }

    /**
     * Method which provide the checking if field is double
     *
     * @param owner owner
     * @param field field
     * @return checking valud
     */
    protected static boolean isInt(@Nullable final Object owner,
                                   @Nullable final Field field) {
        final String methodName = "boolean isInt(owner, field)";
        try {
            field.getInt(owner);
            return true;
        } catch (Exception ex) {
            SQLogHelper.log(null, methodName, ex, null);
        }
        return false;
    }
}
