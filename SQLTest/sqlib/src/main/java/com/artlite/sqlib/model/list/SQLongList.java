package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class SQLongList extends SQLoaderList<Long> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public SQLongList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQLongList(Parcel parcel) {
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
        return Long.class.getClassLoader();
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Creator field
     */
    public static final Creator<SQLongList> CREATOR = new Creator<SQLongList>() {
        @Override
        public SQLongList createFromParcel(Parcel in) {
            return new SQLongList(in);
        }

        @Override
        public SQLongList[] newArray(int size) {
            return new SQLongList[size];
        }
    };
}
