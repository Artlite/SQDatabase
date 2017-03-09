package com.artlite.sqlib.model.list;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public abstract class SQList<T> implements Parcelable, List<T> {
    /**
     * Objects {@link List}
     */
    protected final List<T> objects;

    /**
     * Default constructor
     */
    protected SQList() {
        this.objects = new ArrayList<>();
    }
}
