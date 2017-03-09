package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class SQIntegerList extends SQLoaderList<Integer> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQIntegerList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQIntegerList(Parcel parcel) {
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
        return Integer.class.getClassLoader();
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Creator field
     */
    public static final Creator<SQIntegerList> CREATOR = new Creator<SQIntegerList>() {
        @Override
        public SQIntegerList createFromParcel(Parcel in) {
            return new SQIntegerList(in);
        }

        @Override
        public SQIntegerList[] newArray(int size) {
            return new SQIntegerList[size];
        }
    };


}
