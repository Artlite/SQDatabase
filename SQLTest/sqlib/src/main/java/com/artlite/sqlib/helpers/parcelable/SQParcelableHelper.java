package com.artlite.sqlib.helpers.parcelable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.abs.SQBaseHelper;

import java.io.ByteArrayOutputStream;

/**
 * Class which provide the parsing {@link Object} to {@link Byte} array and back
 * Created by Artli on 25.02.2017.
 */

public final class SQParcelableHelper extends SQBaseHelper {

    private static final int K_MAX_RATIO = 360000;
    private static final int K_MAX_HEIGHT = 600;
    private static final int K_MAX_WIDTH = 600;

    /**
     * Method which provide the writing {@link Parcelable} object to byte array
     *
     * @param object instance of {@link Parcelable}
     * @return byte array
     */
    @NonNull
    public static byte[] convert(@Nullable final Parcelable object) {
        final String methodName = "byte[] convert(object)";
        //Convert Bitmap to bytes array
        if (object instanceof Bitmap) {
            return convert((Bitmap) object);
        }
        //Convert Parcelable to bytes array
        try {
            Parcel parcel = Parcel.obtain();
            object.writeToParcel(parcel, 0);
            byte[] bytes = parcel.marshall();
            parcel.recycle();
            return bytes;
        } catch (Exception ex) {
            log(null, methodName, ex, object);
            return new byte[0];
        }
    }

    /**
     * Method which provide the convert the {@link Bitmap} to {@link Byte} array
     *
     * @param bitmap instance of {@link Bitmap}
     * @return instance of {@link Byte} array
     */
    public static byte[] convert(@Nullable final Bitmap bitmap) {
        final String methodName = "byte[] convert(bitmap)";
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaleIfNeeded(bitmap).compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (Exception ex) {
            log(null, methodName, ex, bitmap);
            return new byte[0];
        }
    }

    /**
     * Method which provide the scaling bitmap if needed
     *
     * @param bitmap instance of {@link Bitmap}
     * @return scaled {@link Bitmap}
     */
    private static Bitmap scaleIfNeeded(@Nullable final Bitmap bitmap) {
        if ((bitmap.getWidth() * bitmap.getHeight()) > K_MAX_RATIO) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) K_MAX_HEIGHT / (float) K_MAX_WIDTH;

            int finalWidth = K_MAX_WIDTH;
            int finalHeight = K_MAX_HEIGHT;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) K_MAX_HEIGHT * ratioBitmap);
            } else {
                finalHeight = (int) ((float) K_MAX_WIDTH / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, false);
        }
        return bitmap;
    }


    /**
     * Method which provide the converting {@link Byte} array to {@link Parcel}
     *
     * @param bytes instance of {@link Byte} array
     * @return instance of {@link Parcel}
     */
    public static Parcel convert(byte[] bytes) {
        final String methodName = "Parcel convert(bytes)";
        try {
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0); // This is extremely important!
            return parcel;
        } catch (Exception ex) {
            log(null, methodName, ex, bytes);
            return null;
        }
    }

    /**
     * Method which provide the converting object from {@link Byte} array and
     * {@link Parcelable.Creator} to object
     *
     * @param bytes   instance of {@link Byte} array
     * @param creator instance of {@link Parcelable.Creator}
     * @param <T>     object type
     * @return instance of object
     */
    public static <T> T convert(byte[] bytes, Parcelable.Creator<T> creator) {
        final String methodName = "T convert(byte[] bytes, Parcelable.Creator<T> creator)";
        try {
            Parcel parcel = convert(bytes);
            T result = creator.createFromParcel(parcel);
            parcel.recycle();
            return result;
        } catch (Exception ex) {
            log(null, methodName, ex, creator);
            return null;
        }
    }

    /**
     * Method which provide the converting of the {@link Byte} array to {@link Bitmap}
     *
     * @param bytes instance of {@link Byte}
     * @return instance of {@link Bitmap}
     */
    public static Bitmap convertToBitmap(@Nullable final byte[] bytes) {
        final String methodName = "Bitmap convertToBitmap(bytes)";
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } catch (Exception ex) {
            log(null, methodName, ex, bytes);
            return null;
        }
    }


    /*==============================================================================================
                                            EXAMPLE
    ================================================================================================
    With the help of the util class above, you can marshall/unmarshall instances of
    your class MyClass implements   Parcelable like so:

    Unmarshalling (with CREATOR)

    byte[] bytes = …
    MyClass myclass = ParcelableUtil.unmarshall(bytes, MyClass.CREATOR);

    Unmarshalling (without CREATOR)

    byte[] bytes = …
    Parcel parcel = ParcelableUtil.unmarshall(bytes);
    MyClass myclass = new MyClass(parcel); // Or MyClass.CREATOR.createFromParcel(parcel).

    Marshalling

    MyClass myclass = …
    byte[] bytes = ParcelableUtil.marshall(myclass);
    */

}
