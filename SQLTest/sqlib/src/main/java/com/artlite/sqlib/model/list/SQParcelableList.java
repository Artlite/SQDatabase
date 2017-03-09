package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public abstract class SQParcelableList<T extends Parcelable> extends SQAbstractList<T> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQParcelableList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQParcelableList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQParcelableList(Parcel parcel) {
        super();
        parcel.readTypedList(this.objects, getCreator());
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Method which provide the describe content for {@link SQParcelableList}
     *
     * @return described content for {@link SQParcelableList}
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
        parcel.writeTypedList(this.objects);
    }

    //==============================================================================================
    //                                       ABSTRACT METHODS
    //==============================================================================================

    /**
     * Method which provide the getting of the {@link Parcelable.Creator}
     *
     * @return instance of {@link Parcelable.Creator}
     */
    @NonNull
    protected abstract Creator<T> getCreator();
}
