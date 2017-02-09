package com.artlite.sqltest.models;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.artlite.adapteredrecyclerview.events.RecycleEvent;
import com.artlite.adapteredrecyclerview.models.BaseObject;
import com.artlite.adapteredrecyclerview.models.BaseRecyclerItem;
import com.artlite.sqlib.annotations.SQField;
import com.artlite.sqlib.helpers.model.SQModelHelper;
import com.artlite.sqlib.model.SQModel;
import com.artlite.sqltest.R;

import java.util.Date;

/**
 * User object
 */

public class User extends BaseObject implements SQModel {

    private static final String K_TABLE_NAME = User.class.getSimpleName();
    public static final RecycleEvent K_DELETE_USER = new RecycleEvent(100);

    private int id;
    @SQField
    private String name;
    @SQField
    private String surname;
    @SQField
    private String aboutMe;
    @SQField
    private Date creation = new Date();

    /**
     * Constructor which provide the create {@link User} from
     *
     * @param name    user name
     * @param surname user surname
     * @param aboutMe user information
     */
    public User(@Nullable final String name,
                @Nullable final String surname,
                @Nullable final String aboutMe) {
        this.name = name;
        this.surname = surname;
        this.aboutMe = aboutMe;
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
     * Method which provide the getting of the {@link BaseRecyclerItem}
     *
     * @param context instance of {@link Context}
     * @return instance of {@link BaseRecyclerItem}
     */
    @Override
    public BaseRecyclerItem getRecyclerItem(@NonNull Context context) {
        return new ObjectView(context);
    }

    //==============================================================================================
    //                                          VIEW
    //==============================================================================================

    /**
     * Class which provide the visualization of the {@link User}
     */
    private static class ObjectView extends BaseRecyclerItem<User> {

        private AppCompatTextView labelName;
        private AppCompatTextView labelAboutMe;
        private ImageView imageDelete;

        /**
         * Default constructor
         *
         * @param context instance of {@link Context}
         */
        public ObjectView(@NonNull final Context context) {
            super(context);
        }

        /**
         * Method which provide the setting up of the {@link ObjectView}
         *
         * @param user instance of {@link User}
         */
        @Override
        public void setUp(@NonNull final User user) {
            labelName.setText(user.getFullName());
            labelAboutMe.setText(user.getAboutMe());
        }

        /**
         * Method which provide the getting of the layout ID
         *
         * @return layout ID value
         */
        @Override
        protected int getLayoutId() {
            return R.layout.recycler_user;
        }

        /**
         * Method which provide the getting of clicked ID
         *
         * @return clicked view container
         */
        @Override
        protected int getClickedID() {
            return R.id.container;
        }

        /**
         * Method which provide the interface linking
         */
        @Override
        protected void onLinkInterface() {
            labelName = (AppCompatTextView) findViewById(R.id.label_name);
            labelAboutMe = (AppCompatTextView) findViewById(R.id.label_about_me);
            imageDelete = (ImageView) findViewById(R.id.image_delete);
        }

        /**
         * Method which provide the action when {@link ObjectView} created
         */
        @Override
        protected void onCreateView() {
            setOnClickListeners(imageDelete);
        }

        /**
         * Method which provide the click listening
         *
         * @param view instance of {@link View}
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_delete: {
                    sendEvent(K_DELETE_USER);
                    break;
                }
                default:
                    break;
            }
        }
    }

}
