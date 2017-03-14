package com.artlite.sqltest.models.user;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.adapteredrecyclerview.models.BaseRecyclerItem;
import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.constants.SQListType;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;
import com.artlite.sqlib.model.SQBitmap;
import com.artlite.sqlib.model.SQModel;
import com.artlite.sqlib.model.list.SQBooleanList;
import com.artlite.sqlib.model.list.SQDoubleList;
import com.artlite.sqlib.model.list.SQFloatList;
import com.artlite.sqlib.model.list.SQIntegerList;
import com.artlite.sqlib.model.list.SQLongList;
import com.artlite.sqlib.model.list.SQStringList;
import com.artlite.sqltest.models.address.Address;
import com.artlite.sqltest.models.address.AddressList;
import com.artlite.sqltest.models.address.AddressMap;

import java.util.Date;

/**
 * User object
 */

public class User extends User_View {

    private int id;
    @SQField
    private String name;
    @SQField
    private String surname;
    @SQField
    private String aboutMe;
    @SQField
    private Date creation = new Date();
    @SQField
    private boolean favorite = false;
    @SQField
    private SQBitmap avatar;
    @SQField
    private Address address;
    //Lists
    @SQField
    private SQBooleanList booleans;
    @SQField
    private SQDoubleList doubles;
    @SQField
    private SQFloatList floats;
    @SQField
    private SQIntegerList integers;
    @SQField
    private SQLongList longs;
    @SQField
    private SQStringList strings;
    @SQField
    private AddressList addresses;
    @SQField
    private AddressMap addressMap;

    /**
     * Constructor which provide the create {@link User} from
     *
     * @param name    user name
     * @param surname user surname
     * @param aboutMe user information
     */
    public User(@Nullable final String name,
                @Nullable final String surname,
                @Nullable final String aboutMe,
                @Nullable final Bitmap avatar) {
        this.name = name;
        this.surname = surname;
        this.aboutMe = aboutMe;
        this.avatar = new SQBitmap(avatar, K_MAX_WIDTH, K_MAX_HEIGHT);
        this.address = new Address();
        //List functional
        this.addresses = new AddressList();
        this.longs = new SQLongList();
        this.booleans = new SQBooleanList();
        this.doubles = new SQDoubleList();
        this.floats = new SQFloatList();
        this.integers = new SQIntegerList();
        this.strings = new SQStringList();
        this.addressMap = new AddressMap();
        //Fill lists
        for (int i = 0; i < 10; i++) {
            this.addresses.add(new Address());
            this.longs.add(new Long(SQRandomHelper.generateInt()));
            this.booleans.add(true);
            this.doubles.add(new Double(SQRandomHelper.generateInt()));
            this.floats.add(new Float(SQRandomHelper.generateInt()));
            this.integers.add(SQRandomHelper.generateInt());
            this.strings.add(SQRandomHelper.generateString(10));
            this.addressMap.put(SQRandomHelper.generateString(10), new Address());
        }
    }

    /**
     * Method which provide the creating {@link User} from {@link Cursor}
     *
     * @param cursor instance of {@link Cursor}
     */
    public User(@NonNull final Cursor cursor) {
        apply(cursor);
    }

    /**
     * Method which provide the getting of the ID fro {@link SQModel}
     *
     * @return id for {@link SQModel}
     */
    @Override
    public int id() {
        return id;
    }

    /**
     * Method which provide the getting of the SQL table name
     *
     * @return table name
     */
    @Nullable
    @Override
    public String table() {
        return K_TABLE_NAME;
    }

    /**
     * Method which provide the applying data from {@link Cursor}
     *
     * @param cursor instance of {@link Cursor}
     */
    @Override
    public void apply(@Nullable Cursor cursor) {
        this.id = SQModelHelper.getID(cursor);
        this.name = SQModelHelper.getString(cursor, "name");
        this.surname = SQModelHelper.getString(cursor, "surname");
        this.aboutMe = SQModelHelper.getString(cursor, "aboutMe");
        this.creation = SQModelHelper.getDate(cursor, "creation");
        this.address = SQModelHelper.getObject(cursor, Address.CREATOR, "address");
        this.avatar = SQModelHelper.getObject(cursor, SQBitmap.CREATOR, "avatar");
        //Get lists
        this.addresses = SQModelHelper.getList(cursor, AddressList.CREATOR, "addresses");
        this.booleans = SQModelHelper.getList(cursor, SQListType.BOOLEAN, "booleans");
        this.doubles = SQModelHelper.getList(cursor, SQListType.DOUBLE, "doubles");
        this.floats = SQModelHelper.getList(cursor, SQListType.FLOAT, "floats");
        this.integers = SQModelHelper.getList(cursor, SQListType.INTEGER, "integers");
        this.strings = SQModelHelper.getList(cursor, SQListType.STRING, "strings");
        this.addressMap = SQModelHelper.getMap(cursor, AddressMap.CREATOR, "addressMap");
        setFavorite(SQModelHelper.getBoolean(cursor, "favorite"));
    }

    /**
     * Method which provide the name getting
     *
     * @return name value
     */
    @NonNull
    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    /**
     * Method which provide the name setting
     *
     * @param name name value
     */
    public void setName(@Nullable final String name) {
        this.name = name;
    }

    /**
     * Method which provide the getting of the {@link User} surname
     *
     * @return surname value
     */
    @NonNull
    public String getSurname() {
        if (surname == null) {
            surname = "";
        }
        return surname;
    }

    /**
     * Method which provide the setting of the {@link User} surname
     *
     * @param surname surname value
     */
    public void setSurname(@Nullable final String surname) {
        this.surname = surname;
    }

    /**
     * Method which provide the getting of the about me
     *
     * @return about me value
     */
    @NonNull
    public String getAboutMe() {
        if (aboutMe == null) {
            aboutMe = "";
        }
        return aboutMe;
    }

    /**
     * Method which provide the setting of the {@link User} about me
     *
     * @param aboutMe about me value
     */
    public void setAboutMe(@Nullable final String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * Method which provide the getting of the {@link User} creation date
     *
     * @return instance of {@link Date}
     */
    @NonNull
    public Date getCreation() {
        return creation;
    }

    /**
     * Method which provide the getting of the full name
     *
     * @return full name value
     */
    @NonNull
    public String getFullName() {
        return String.format("%s %s", getName(), getSurname());
    }

    /**
     * Method which provide the checking if user favorite
     *
     * @return if user favorite
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * Method which provide the setting of the favorite
     *
     * @param favorite if user is favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        if (favorite) {
            setPriority(Priority.HIGHT);
        } else {
            setPriority(Priority.MIDDLE);
        }
    }

    /**
     * Method which provide the switching favorite
     */
    public void switchFavorite() {
        setFavorite(!favorite);
    }

    /**
     * Method which provide the getting of the {@link User} avatar
     *
     * @return instance of {@link Bitmap}
     */
    @Nullable
    public Bitmap getAvatar() {
        if (SQValidationHelper.isEmpty(avatar)) {
            return null;
        }
        return avatar.getBitmap();
    }

    /**
     * Method which provide the setting of the {@link User} avatar
     *
     * @param avatar instance of {@link Bitmap}
     */
    public void setAvatar(Bitmap avatar) {
        this.avatar = new SQBitmap(avatar, K_MAX_WIDTH, K_MAX_HEIGHT);
    }

    /**
     * Method which provide the getting of the {@link BaseRecyclerItem}
     *
     * @param context instance of {@link Context}
     * @return instance of {@link BaseRecyclerItem}
     */
    @Override
    public BaseRecyclerItem getRecyclerItem(@NonNull Context context) {
        return new ObjectView(context);
    }

    /**
     * Method which provide the convert {@link User} to {@link String}
     *
     * @return description of {@link User}
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", creation=" + creation +
                ", favorite=" + favorite +
                '}';
    }

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
        User user = (User) object;
        return id == user.id;
    }

    /**
     * Method which provide the hash code functional for {@link User}
     *
     * @return hash code for {@link User}
     */
    @Override
    public int hashCode() {
        return id;
    }

    //==============================================================================================
    //                                   PARCELABLE
    //==============================================================================================

    /**
     * Method which provide the describing content for the {@link User}
     *
     * @return describing content of the {@link User}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the write {@link User} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  args value
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.surname);
        parcel.writeString(this.aboutMe);
        parcel.writeLong(this.creation != null ? this.creation.getTime() : -1);
        parcel.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.avatar, flags);
        parcel.writeParcelable(this.address, flags);
    }

    /**
     * Constructor which provide the create of the {@link User} from
     *
     * @param parcel instance of {@link Parcel}
     */
    protected User(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.surname = parcel.readString();
        this.aboutMe = parcel.readString();
        long tmpCreation = parcel.readLong();
        this.creation = tmpCreation == -1 ? null : new Date(tmpCreation);
        this.favorite = parcel.readByte() != 0;
        this.avatar = parcel.readParcelable(SQBitmap.class.getClassLoader());
        this.address = parcel.readParcelable(Address.class.getClassLoader());
    }

    /**
     * Creator field
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
