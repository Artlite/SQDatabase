package com.artlite.sqlib.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.artlite.sqlib.helpers.parcelable.SQParcelableHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.log.SQLoggableObject;

/**
 * Class which provide the create {@link android.graphics.Bitmap} with maximum resolution
 * Created by dlernatovich on 3/6/2017.
 */

public final class SQBitmap extends SQLoggableObject implements Parcelable {

    private final int heigh;
    private final int width;
    private Bitmap bitmap;

    public SQBitmap(int width, int heigh) {
        this.heigh = heigh;
        this.width = width;
    }

    protected SQBitmap(Parcel in) {

    }

    public static final Creator<SQBitmap> CREATOR = new Creator<SQBitmap>() {
        @Override
        public SQBitmap createFromParcel(Parcel in) {
            return new SQBitmap(in);
        }

        @Override
        public SQBitmap[] newArray(int size) {
            return new SQBitmap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray();
    }

    /**
     * Method which provide the getting of the {@link Bitmap} {@link Byte} array
     *
     * @return
     */
    private byte[] getBitmapArray() {
        if (!SQValidationHelper.isEmpty(bitmap)) {
            return SQParcelableHelper.convert(this.bitmap);
        }
        return new byte[0];
    }
}
