package com.artlite.sqlib.helpers.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.convert.SQConvertHelper;
import com.artlite.sqlib.helpers.parcelable.SQParcelableHelper;
import com.artlite.sqlib.log.SQLoggableObject;

import java.util.Date;

/**
 * Class which provide the converting the functional
 * Created by dlernatovich on 12/22/2016.
 */

abstract class SQModelHelper_Convert extends SQLoggableObject {

    /**
     * Method which provide the converting object to bytes
     *
     * @param object object
     * @return object bytes
     */
    @NonNull
    protected static byte[] convert(@Nullable final Parcelable object) {
        return SQParcelableHelper.convert(object);
    }

    /**
     * Method which provide the converting the bytes array to {@link Object}
     *
     * @param bytes   bytes
     * @param creator instance of {@link Parcelable.Creator}
     * @return object
     */
    @Nullable
    protected static <T> T convert(@Nullable final byte[] bytes, Parcelable.Creator<T> creator) {
        return SQParcelableHelper.convert(bytes, creator);
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    protected static String convert(@Nullable final Date date) {
        return SQConvertHelper.convert(date);
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    protected static Date convert(@Nullable final String date) {
        return SQConvertHelper.convert(date);
    }
}
