package com.artlite.sqlib.callbacks;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.model.SQModel;

/**
 * Method which provide the action when cursor found
 * Created by Artli on 24.12.2016.
 */

public interface SQCursorCallback<T extends SQModel> {
    /**
     * Method which provide the converting functional from {@link Cursor} to {@link SQModel}
     *
     * @param cursor instance of {@link Cursor}
     * @return instance of {@link SQModel}
     */
    @Nullable
    T convert(@NonNull final Cursor cursor);
}
