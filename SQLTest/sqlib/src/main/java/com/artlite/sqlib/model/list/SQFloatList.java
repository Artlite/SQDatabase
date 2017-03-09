package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class SQFloatList extends SQLoaderList<Float> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQFloatList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQFloatList(Parcel parcel) {
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
        return Float.class.getClassLoader();
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Creator field
     */
    public static final Creator<SQFloatList> CREATOR = new Creator<SQFloatList>() {
        @Override
        public SQFloatList createFromParcel(Parcel in) {
            return new SQFloatList(in);
        }

        @Override
        public SQFloatList[] newArray(int size) {
            return new SQFloatList[size];
        }
    };
}
