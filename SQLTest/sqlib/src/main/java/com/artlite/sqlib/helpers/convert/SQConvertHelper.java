package com.artlite.sqlib.helpers.convert;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.abs.SQBaseHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class which provide the converting Objects to string and back
 * Created by Artli on 30.12.2016.
 */

public final class SQConvertHelper extends SQBaseHelper {

    /**
     * Constants
     */
    private static String K_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * Method which provide the converting object to bytes
     *
     * @param object object
     * @return object bytes
     */
    @SuppressLint("NewApi")
    @NonNull
    public static byte[] convert(@Nullable final Object object) {
        final String methodName = "byte[] convert(object)";
        try {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutput out = new ObjectOutputStream(bos)) {
                out.writeObject(object);
                return bos.toByteArray();
            }
        } catch (Exception e) {
            log(null, methodName, e, null);
        }
        return new byte[]{};
    }

    /**
     * Method which provide the converting the bytes array to {@link Object}
     *
     * @param bytes bytes
     * @return object
     */
    @SuppressLint("NewApi")
    @Nullable
    public static Object convert(@Nullable final byte[] bytes) {
        final String methodName = "Object convert(bytes)";
        try {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInput in = new ObjectInputStream(bis)) {
                return in.readObject();
            }
        } catch (Exception e) {
            log(null, methodName, e, null);
        }
        return null;
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    public static String convert(@Nullable final Date date) {
        final String methodName = "String convert(date)";
        try {
            return new SimpleDateFormat(K_FORMAT_DATE).format(date);
        } catch (Exception e) {
            log(null, methodName, e, null);
        }
        return null;
    }

    /**
     * Method which provide the converting of the {@link Date} to {@link String}
     *
     * @param date instance of {@link Date}
     * @return converted {@link String}
     */
    @Nullable
    public static Date convert(@Nullable final String date) {
        final String methodName = "String convert(date)";
        try {
            return new SimpleDateFormat(K_FORMAT_DATE).parse(date);
        } catch (Exception e) {
            log(null, methodName, e, null);
        }
        return null;
    }
}
