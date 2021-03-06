package com.artlite.sqlib.model;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

/**
 * Class which provide the creating of the SQL model
 * Created by dlernatovich on 12/22/2016.
 */

public interface SQModel extends BaseColumns {

    /**
     * Method which provide the getting of the ID fro {@link SQModel}
     *
     * @return id for {@link SQModel}
     */
    int id();

    /**
     * Method which provide the getting of the SQL table name
     *
     * @return table name
     */
    @Nullable
    String table();

    /**
     * Method which provide the applying data from {@link Cursor}
     *
     * @param cursor instance of {@link Cursor}
     */
    void apply(@Nullable final Cursor cursor);
}
