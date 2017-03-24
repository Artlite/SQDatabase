package com.artlite.sqltest.models.address;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.model.map.SQMap;

/**
 * Created by dlernatovich on 3/14/2017.
 */

public final class AddressMap extends SQMap<String, Address> {

    /**
     * Constructor which provide the create {@link SQMap}
     */
    public AddressMap() {
        super();
    }

    /**
     * Method which provide the create {@link SQMap} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    public AddressMap(Parcel parcel) {
        super(parcel);
    }

    /**
     * Method which provide the writing {@link SQMap} key to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     * @param key    instance of {@link Object}
     */
    @Override
    protected void writeKey(@NonNull Parcel parcel, int flags, @NonNull String key) {
        parcel.writeString(key);
    }

    /**
     * Method which provide the writing {@link SQMap} value to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     * @param value  instance of {@link Object}
     */
    @Override
    protected void writeValue(@NonNull Parcel parcel, int flags, @NonNull Address value) {
        parcel.writeParcelable(value, flags);
    }

    /**
     * Method which provide the reading key {@link Object} from the {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @return object
     */
    @Nullable
    @Override
    protected String readKey(@NonNull Parcel parcel) {
        return parcel.readString();
    }

    /**
     * Method which provide the reading value {@link Object} from the {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @return object
     */
    @Nullable
    @Override
    protected Address readValue(@NonNull Parcel parcel) {
        return parcel.readParcelable(Address.class.getClassLoader());
    }

    /**
     * Field which provide to create {@link AddressMap} from {@link Parcel}
     */
    public static final Creator<AddressMap> CREATOR = new Creator<AddressMap>() {
        @Override
        public AddressMap createFromParcel(Parcel in) {
            return new AddressMap(in);
        }

        @Override
        public AddressMap[] newArray(int size) {
            return new AddressMap[size];
        }
    };
}
