package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.Constants;
import com.artlite.sqlib.helpers.random.SQRandomHelper;

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
    protected final String id;

    /**
     * Default constructor
     */
    protected SQList() {
        this.objects = new ArrayList<>();
        this.id = SQRandomHelper.generateString(Constants.K_ID_LENGTH);
    }

    //==============================================================================================
    //                                    PARCELABLE
    //==============================================================================================

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQList(Parcel parcel) {
        this.objects = new ArrayList<>();
        this.id = parcel.readString();
    }

    /**
     * Method which provide the write {@link SQParcelableList} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
    }

    //==============================================================================================
    //                                    EQUALS/HASH CODE
    //==============================================================================================

    /**
     * Method which provide the equaling of the {@link SQList} objects
     *
     * @param object instance of {@link Object}
     * @return equaling objects
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SQList<?> sqList = (SQList<?>) object;
        return id.equals(sqList.id);

    }

    /**
     * Method which provide the generating the hash code for {@link SQList}
     *
     * @return generated hash code for {@link SQList}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Method which provide the getting of the object {@link List}
     *
     * @return object {@link List}
     */
    @NonNull
    public List<T> getList() {
        return objects;
    }

    /**
     * Method which provide the setting of the {@link List} of objects
     *
     * @param objects {@link List} of objects
     */
    public void setList(@Nullable final List<T> objects) {
        this.objects.clear();
        this.objects.addAll(objects);
    }
}
