package com.artlite.sqltest.models.address;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.artlite.sqlib.model.list.SQParcelableList;

/**
 * Created by dlernatovich on 3/9/2017.
 */

public final class AddressList extends SQParcelableList<Address> {

    //==============================================================================================
    //                                      CONSTRUCTORS
    //==============================================================================================

    /**
     * Default constructor
     */
    public AddressList() {
        super();
    }

    /**
     * Constructor which provide the create {@link AddressList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected AddressList(Parcel parcel) {
        super(parcel);
    }

    //==============================================================================================
    //                                       PARCELABLE
    //==============================================================================================

    /**
     * Method which provide the getting of the {@link Parcelable.Creator}
     *
     * @return instance of {@link Parcelable.Creator}
     */
    @NonNull
    @Override
    protected Creator<Address> getCreator() {
        return Address.CREATOR;
    }

    /**
     * Creator field
     */
    public static final Creator<AddressList> CREATOR = new Creator<AddressList>() {
        @Override
        public AddressList createFromParcel(Parcel in) {
            return new AddressList(in);
        }

        @Override
        public AddressList[] newArray(int size) {
            return new AddressList[size];
        }
    };

}
