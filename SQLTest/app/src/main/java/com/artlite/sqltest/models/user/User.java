package com.artlite.sqltest.models.user;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.adapteredrecyclerview.models.BaseRecyclerItem;
import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.model.SQModel;

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
    private Bitmap bitmap;

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
                @Nullable final Bitmap bitmap) {
        this.name = name;
        this.surname = surname;
        this.aboutMe = aboutMe;
        this.bitmap = bitmap;
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
}
