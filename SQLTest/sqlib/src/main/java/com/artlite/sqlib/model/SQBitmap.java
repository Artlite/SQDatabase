package com.artlite.sqlib.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.artlite.sqlib.helpers.bitmap.SQBitmapHelper;
import com.artlite.sqlib.helpers.parcelable.SQParcelableHelper;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.log.SQLoggableObject;

/**
 * Class which provide the create {@link android.graphics.Bitmap} with maximum resolution
 */

public final class SQBitmap extends SQLoggableObject implements Parcelable {

    //==============================================================================================
    //                                     CONSTANTS
    //==============================================================================================

    /**
     * ID length
     */
    private static final int K_ID_LENGTH = 20;

    //==============================================================================================
    //                                       FIELDS
    //==============================================================================================

    /**
     * Bitmap ID
     */
    private final String id;
    /**
     * Maximum height for {@link Bitmap}
     */
    private final int height;
    /**
     * Maximum width for {@link Bitmap}
     */
    private final int width;
    /**
     * {@link Bitmap}
     */
    private Bitmap bitmap;

    //==============================================================================================
    //                                     CONSTRUCTORS
    //==============================================================================================

    /**
     * Constructor which provide the create of the {@link SQBitmap} with maximum
     *
     * @param width  width value
     * @param height height value
     */
    public SQBitmap(@IntRange(from = 1, to = 600) int width,
                    @IntRange(from = 1, to = 600) int height) {
        this.height = height;
        this.width = width;
        this.id = SQRandomHelper.generateString(K_ID_LENGTH);
    }

    /**
     * Constructor which provide the create of the {@link SQBitmap} with maximum
     *
     * @param bitmap instance of {@link Bitmap}
     * @param width  width value
     * @param height height value
     */
    public SQBitmap(@Nullable final Bitmap bitmap,
                    @IntRange(from = 1, to = 600) int width,
                    @IntRange(from = 1, to = 600) int height) {
        this(width, height);
        setBitmap(bitmap);
    }

    //==============================================================================================
    //                                     PARCELABLE
    //==============================================================================================

    /**
     * Constructor which provide the create of the {@link SQBitmap} from
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQBitmap(Parcel parcel) {
        bitmap = SQBitmapHelper.convert(parcel.createByteArray());
        width = parcel.readInt();
        height = parcel.readInt();
        id = parcel.readString();
    }

    /**
     * Method which provide the describing content for the {@link SQBitmap}
     *
     * @return describing content of the {@link SQBitmap}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the write {@link SQBitmap} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param i      args value
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(getBitmapArray());
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(id);
    }

    /**
     * Creator field
     */
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

    //==============================================================================================
    //                                    TO STRING/EQUALS
    //==============================================================================================

    /**
     * Method which provide the equaling of the objects
     *
     * @param object instance of {@link Object}
     * @return equaling results
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SQBitmap sqBitmap = (SQBitmap) object;
        return id.equals(sqBitmap.id);

    }

    /**
     * Method which provide the hash code functional for {@link SQBitmap}
     *
     * @return hash code for {@link SQBitmap}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Method which provide the {@link String} representation of the {@link SQBitmap}
     *
     * @return {@link String} representation of the {@link SQBitmap}
     */
    @Override
    public String toString() {
        return "SQBitmap{" +
                "id='" + id + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", bitmap=" + bitmap +
                "} " + super.toString();
    }

    //==============================================================================================
    //                                     GETTER/SETTERS
    //==============================================================================================

    /**
     * Method which provide the getting of the {@link Bitmap} {@link Byte} array
     *
     * @return {@link Byte} array from {@link Bitmap}
     */
    private byte[] getBitmapArray() {
        if (!SQValidationHelper.isEmpty(bitmap)) {
            return SQParcelableHelper.convert(this.bitmap);
        }
        return new byte[0];
    }

    /**
     * Method which provide the setting of the {@link Bitmap} into {@link SQBitmap}
     *
     * @param bitmap instance of {@link Bitmap}
     */
    public final void setBitmap(@Nullable final Bitmap bitmap) {
        this.bitmap = SQBitmapHelper.scale(bitmap, width, height);
    }

    /**
     * Method which provide the getting of the instance of {@link Bitmap} from {@link SQBitmap}
     *
     * @return instance of {@link Bitmap}
     */
    @Nullable
    public final Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Method which provide the getting of the ID for {@link SQBitmap}
     *
     * @return ID value for {@link SQBitmap}
     */
    public String getId() {
        return id;
    }

    /**
     * Method which provide the getting of the height for {@link SQBitmap}
     *
     * @return height value for {@link SQBitmap}
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method which provide the getting of the width for {@link SQBitmap}
     *
     * @return width value for {@link SQBitmap}
     */
    public int getWidth() {
        return width;
    }
}
