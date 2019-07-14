package com.artlite.sqltest.models.user;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.artlite.adapteredrecyclerview.events.AREvent;
import com.artlite.adapteredrecyclerview.models.ARCell;
import com.artlite.adapteredrecyclerview.models.ARObject;
import com.artlite.sqlib.model.SQModel;
import com.artlite.sqltest.R;

/**
 * Created by dlernatovich on 2/15/2017.
 */

abstract class User_View extends ARObject implements SQModel, Parcelable {

    protected static final String K_TABLE_NAME = User.class.getSimpleName();
    public static final AREvent K_DELETE_USER = new AREvent(100);
    public static final AREvent K_ADD_TO_FAVORITE = new AREvent(101);
    protected static final int K_MAX_WIDTH = 150;
    protected static final int K_MAX_HEIGHT = 150;

    //==============================================================================================
    //                                          VIEW
    //==============================================================================================

    /**
     * Class which provide the visualization of the {@link User}
     */
    protected static class ObjectView extends ARCell<User> {

        //        @FindViewBy(id = R.id.label_name)
        private AppCompatTextView labelName;
        //        @FindViewBy(id = R.id.label_about_me)
        private AppCompatTextView labelAboutMe;
        //        @FindViewBy(id = R.id.image_delete)
        private ImageView imageDelete;
        //        @FindViewBy(id = R.id.layout_image)
        private View layoutImage;
        //        @FindViewBy(id = R.id.image_like)
        private ImageView imageLike;
        //        @FindViewBy(id = R.id.image_avatar)
        private ImageView imageAvatar;

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
            imageLike.setVisibility((user.isFavorite() == true)
                    ? VISIBLE : GONE);
            imageAvatar.setImageBitmap(user.getAvatar());
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
         * Method which provide the action when {@link ObjectView} created
         */
        @Override
        protected void onCreateView() {
            labelName = findViewById(R.id.label_name);
            labelAboutMe = findViewById(R.id.label_about_me);
            imageDelete = findViewById(R.id.image_delete);
            layoutImage = findViewById(R.id.layout_image);
            imageLike = findViewById(R.id.image_like);
            imageAvatar = findViewById(R.id.image_avatar);
            setOnClickListeners(imageDelete, layoutImage);
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
                case R.id.layout_image: {
                    sendEvent(K_ADD_TO_FAVORITE);
                    break;
                }
                default:
                    break;
            }
        }
    }
}
