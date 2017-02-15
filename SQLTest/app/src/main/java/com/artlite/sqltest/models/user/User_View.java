package com.artlite.sqltest.models.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.artlite.adapteredrecyclerview.events.RecycleEvent;
import com.artlite.adapteredrecyclerview.models.BaseObject;
import com.artlite.adapteredrecyclerview.models.BaseRecyclerItem;
import com.artlite.sqlib.model.SQModel;
import com.artlite.sqltest.R;

/**
 * Created by dlernatovich on 2/15/2017.
 */

abstract class User_View extends BaseObject implements SQModel {

    protected static final String K_TABLE_NAME = User.class.getSimpleName();
    public static final RecycleEvent K_DELETE_USER = new RecycleEvent(100);
    public static final RecycleEvent K_ADD_TO_FAVORITE = new RecycleEvent(101);

    //==============================================================================================
    //                                          VIEW
    //==============================================================================================

    /**
     * Class which provide the visualization of the {@link User}
     */
    protected static class ObjectView extends BaseRecyclerItem<User> {

        private AppCompatTextView labelName;
        private AppCompatTextView labelAboutMe;
        private ImageView imageDelete;
        private View layoutImage;
        private ImageView imageLike;

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
            layoutImage = findViewById(R.id.layout_image);
            imageLike = (ImageView) findViewById(R.id.image_like);
        }

        /**
         * Method which provide the action when {@link ObjectView} created
         */
        @Override
        protected void onCreateView() {
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
