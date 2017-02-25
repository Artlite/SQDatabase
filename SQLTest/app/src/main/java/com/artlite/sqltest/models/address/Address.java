package com.artlite.sqltest.models.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.artlite.sqlib.helpers.random.SQRandomHelper;

/**
 * Created by Artli on 25.02.2017.
 */

public final class Address implements Parcelable {

    private static final String TAG = Address.class.getName();

    private String name = SQRandomHelper.generate(10);
    private String address = SQRandomHelper.generate(10);
    private int priority = SQRandomHelper.generate();

    /**
     * Default constructor
     */
    public Address() {
    }

    /**
     * Constructor which provide the create {@link Address} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected Address(Parcel parcel) {
        name = parcel.readString();
        address = parcel.readString();
        priority = parcel.readInt();
    }

    /**
     * Method which provide the describe content for {@link Address}
     *
     * @return describing content from {@link Address}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the writing {@link Address} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeInt(priority);
    }

    /**
     * Method which provide the converting {@link Address} to {@link String} representation
     *
     * @return {@link String} representation of {@link Address}
     */
    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * Field which provide to create {@link Address} from {@link Parcel}
     */
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
