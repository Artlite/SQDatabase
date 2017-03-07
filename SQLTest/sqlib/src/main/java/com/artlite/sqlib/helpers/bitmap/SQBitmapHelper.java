package com.artlite.sqlib.helpers.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQLimitation;
import com.artlite.sqlib.helpers.abs.SQBaseHelper;

import java.io.ByteArrayOutputStream;

/**
 * Class which provide the functional for {@link Bitmap}
 */

public final class SQBitmapHelper extends SQBaseHelper {

    private static final int K_MAX_HEIGHT = 600;
    private static final int K_MAX_WIDTH = 600;
    private static final int K_MAX_RATIO = K_MAX_HEIGHT * K_MAX_WIDTH;

    /**
     * Method which provide the scaling bitmap if needed
     *
     * @param bitmap instance of {@link Bitmap}
     * @return scaled {@link Bitmap}
     */
    @Nullable
    @SQLimitation(message = "Maximum Bitmap resolution can be 600x600")
    public static Bitmap scale(@Nullable final Bitmap bitmap) {
        return scale(bitmap, K_MAX_WIDTH, K_MAX_HEIGHT);
    }

    /**
     * Method which provide the scaling bitmap if needed
     *
     * @param bitmap instance of {@link Bitmap}
     * @return scaled {@link Bitmap}
     */
    @Nullable
    @SQLimitation(message = "Maximum Bitmap resolution can be 600x600")
    public static Bitmap scale(@Nullable final Bitmap bitmap,
                               @IntRange(from = 1, to = K_MAX_WIDTH) int maxWidth,
                               @IntRange(from = 1, to = K_MAX_WIDTH) int maxHeight) {
        final int currentRatio = (bitmap == null)
                ? K_MAX_RATIO : (bitmap.getWidth() * bitmap.getHeight());
        if (currentRatio > K_MAX_RATIO) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxHeight / (float) maxWidth;
            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, false);
        }
        return bitmap;
    }

    /**
     * Method which provide the convert the {@link Bitmap} to {@link Byte} array
     *
     * @param bitmap instance of {@link Bitmap}
     * @return instance of {@link Byte} array
     */
    @NonNull
    public static byte[] convert(@Nullable final Bitmap bitmap) {
        final String methodName = "byte[] convert(bitmap)";
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            SQBitmapHelper.scale(bitmap).compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (Exception ex) {
            log(null, methodName, ex, bitmap);
            return new byte[0];
        }
    }

    /**
     * Method which provide the converting of the {@link Byte} array to {@link Bitmap}
     *
     * @param bytes instance of {@link Byte}
     * @return instance of {@link Bitmap}
     */
    @Nullable
    public static Bitmap convert(@Nullable final byte[] bytes) {
        final String methodName = "Bitmap convert(bytes)";
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } catch (Exception ex) {
            log(null, methodName, ex, bytes);
            return null;
        }
    }
}
