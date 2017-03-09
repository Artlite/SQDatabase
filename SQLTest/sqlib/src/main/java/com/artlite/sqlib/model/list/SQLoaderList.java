package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

abstract class SQLoaderList<T> extends SQAbstractList<T> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQLoaderList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQLoaderList(Parcel parcel) {
        super();
        parcel.readList(this.objects, getLoader());
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Method which provide the describe content for {@link SQLoaderList}
     *
     * @return described content for {@link SQLoaderList}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the write {@link SQParcelableList} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeList(this.objects);
    }

    //==============================================================================================
    //                                       ABSTRACT METHODS
    //==============================================================================================

    /**
     * Method which provide the getting of the {@link ClassLoader}
     *
     * @return instance of {@link ClassLoader}
     */
    @NonNull
    protected abstract ClassLoader getLoader();
}
