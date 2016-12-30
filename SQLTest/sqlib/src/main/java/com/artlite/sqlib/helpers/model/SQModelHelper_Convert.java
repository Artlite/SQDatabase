package com.artlite.sqlib.helpers.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.convert.SQConvertHelper;
import com.artlite.sqlib.log.SQLoggableObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dlernatovich on 12/22/2016.
 */

abstract class SQModelHelper_Convert extends SQLoggableObject {

    /**
     * Method which provide the converting object to bytes
     *
     * @param object object
     * @return object bytes
     */
    @SuppressLint("NewApi")
    @NonNull
    protected static byte[] convert(@Nullable final Object object) {
        return SQConvertHelper.convert(object);
    }

    /**
     * Method which provide the converting the bytes array to {@link Object}
     *
     * @param bytes bytes
     * @return object
     */
    @SuppressLint("NewApi")
    @Nullable
    protected static Object convert(@Nullable final byte[] bytes) {
        return SQConvertHelper.convert(bytes);
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
