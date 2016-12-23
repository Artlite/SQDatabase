package com.artlite.sqltest;

import android.support.annotation.Nullable;

import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.model.SQModel;

/**
 * Created by Artli on 23.12.2016.
 */

public class SQClass implements SQModel {

    @SQField
    private final String name = SQRandomHelper.generate(10);
    @SQField
    private final int userCount = SQRandomHelper.generate();
    @SQField
    private final boolean isSPecial = true;
    @SQField
    private final float floatCount = SQRandomHelper.generate();

    @Nullable
    @Override
    public String table() {
        return SQClass.class.getSimpleName();
    }
}
