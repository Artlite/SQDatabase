package com.artlite.sqltest;

import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.constants.SQType;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.model.SQModel;

import java.util.Date;

/**
 * Created by dlernatovich on 12/22/2016.
 */

public class SQLUser implements SQModel {

    @SQField
    private final String name = SQRandomHelper.generate(100);
    @SQField
    private float floatValue = 0.10f;
    @SQField
    private double doubleValue = 0.5;
    @SQField
    private final Date dateValue = new Date();


    /**
     * Method which provide the getting of the SQL table name
     *
     * @return table name
     */
    @Nullable
    @Override
    public String table() {
        return SQLUser.class.getSimpleName();
    }
}
