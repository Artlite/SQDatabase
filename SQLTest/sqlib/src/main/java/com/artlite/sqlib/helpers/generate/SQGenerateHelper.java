package com.artlite.sqlib.helpers.generate;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.artlite.sqlib.constants.SQType;
import com.artlite.sqlib.helpers.abs.SQBaseHelper;
import com.artlite.sqlib.helpers.annotation.SQAnnotationHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class which provide the generating functional for objects
 * Created by dlernatovich on 2/8/2017.
 */

public final class SQGenerateHelper extends SQBaseHelper {

    /**
     * Method which provide the generate object description via reflection
     *
     * @param object instance of {@link Object}
     * @return description value
     */
    public static String generateDescription(@Nullable final Object object) {
        final String methdoName = "void generateDescription(object)";
        final StringBuilder builder = new StringBuilder();
        final List<String> values = new ArrayList<>();
        if (validate(object)) {
            final Class aClass = object.getClass();
            final Field[] fields = aClass.getDeclaredFields();
            builder.append(aClass.getSimpleName()).append(" { ");
            if (validate(fields)) {
                for (final Field field : fields) {
                    field.setAccessible(true);
                    final SQType type = SQType.getType(field);
                    if (validate(type)) {
                        Object value = null;
                        switch (type) {
                            case INTEGER:
                                value = SQAnnotationHelper.getFieldValue(object, field, Integer.class);
                                break;
                            case BLOB:
                                value = SQAnnotationHelper.getFieldValue(object, field, Object.class);
                                break;
                            case BOOLEAN:
                                value = SQAnnotationHelper.getFieldValue(object, field, Boolean.class);
                                break;
                            case DATE:
                                value = SQAnnotationHelper.getFieldValue(object, field, Date.class);
                                break;
                            case DOUBLE:
                            case FLOAT:
                                value = SQAnnotationHelper.getFieldValue(object, field, Float.class);
                                if (value == null) {
                                    value = SQAnnotationHelper.getFieldValue(object, field, Double.class);
                                }
                                break;
                            case VARCHAR:
                            case STRING:
                                value = SQAnnotationHelper.getFieldValue(object, field, String.class);
                                break;
                        }
                        if (validate(value)) {
                            values.add(String.format("%s = \"%s\"", field.getName(), value));
                        }
                    }
                }
                builder.append(TextUtils.join(", ", values));
            }
            builder.append(" }");
        }
        return builder.toString();
    }

}
