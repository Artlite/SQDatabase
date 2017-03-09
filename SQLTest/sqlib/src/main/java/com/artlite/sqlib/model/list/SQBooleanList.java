package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class SQBooleanList extends SQLoaderList<Boolean> {
    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQBooleanList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQBooleanList(Parcel parcel) {
        super(parcel);
    }

    /**
     * Method which provide the getting of the {@link ClassLoader}
     *
     * @return instance of {@link ClassLoader}
     */
    @NonNull
    @Override
    protected ClassLoader getLoader() {
        return Boolean.class.getClassLoader();
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Creator field
     */
    public static final Creator<SQBooleanList> CREATOR = new Creator<SQBooleanList>() {
        @Override
        public SQBooleanList createFromParcel(Parcel in) {
            return new SQBooleanList(in);
        }

        @Override
        public SQBooleanList[] newArray(int size) {
            return new SQBooleanList[size];
        }
    };
}
