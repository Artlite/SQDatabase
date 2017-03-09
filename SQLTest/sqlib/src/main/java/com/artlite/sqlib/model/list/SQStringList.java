package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class SQStringList extends SQLoaderList<String> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQStringList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQStringList(Parcel parcel) {
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
        return String.class.getClassLoader();
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Creator field
     */
    public static final Creator<SQStringList> CREATOR = new Creator<SQStringList>() {
        @Override
        public SQStringList createFromParcel(Parcel in) {
            return new SQStringList(in);
        }

        @Override
        public SQStringList[] newArray(int size) {
            return new SQStringList[size];
        }
    };
}
