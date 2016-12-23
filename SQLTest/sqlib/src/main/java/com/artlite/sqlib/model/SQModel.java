package com.artlite.sqlib.model;

import android.provider.BaseColumns;
import android.support.annotation.Nullable;

/**
 * Class which provide the creating of the SQL model
 * Created by dlernatovich on 12/22/2016.
 */

public interface SQModel extends BaseColumns {
    /**
     * Method which provide the getting of the SQL table name
     *
     * @return table name
     */
    @Nullable
    String table();
}
