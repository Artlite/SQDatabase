package com.artlite.sqltest;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.model.SQModel;

import java.util.Date;

/**
 * Created by Artli on 23.12.2016.
 */

public class SQClass implements SQModel {

    private int id;
    @SQField
    public String name = SQRandomHelper.generate(10);
    @SQField
    private int userCount = SQRandomHelper.generate();
    @SQField
    private boolean isSpecial = true;
    @SQField
    private float floatCount = SQRandomHelper.generate();
    @SQField
    private Date currentDate = new Date();

    public SQClass() {

    }

    public SQClass(@Nullable final Cursor cursor) {
        this();
        apply(cursor);
    }

    @Override
    public int id() {
        return id;
    }

    @Nullable
    @Override
    public String table() {
        return SQClass.class.getSimpleName();
    }

    @Override
    public void apply(@Nullable Cursor cursor) {
        id = SQModelHelper.getID(cursor);
        name = SQModelHelper.getString(cursor, "name");
        userCount = SQModelHelper.getInteger(cursor, "userCount");
        isSpecial = SQModelHelper.getBoolean(cursor, "isSpecial");
        floatCount = SQModelHelper.getFloat(cursor, "floatCount");
        currentDate = SQModelHelper.getDate(cursor, "currentDate");
    }
}
