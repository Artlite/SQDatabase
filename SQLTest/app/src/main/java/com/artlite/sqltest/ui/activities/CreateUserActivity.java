package com.artlite.sqltest.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.artlite.adapteredrecyclerview.anotations.FindViewBy;
import com.artlite.adapteredrecyclerview.helpers.AdapteredInjector;
import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.log.SQLogHelper;
import com.artlite.sqltest.R;
import com.artlite.sqltest.constants.EventCodes;
import com.artlite.sqltest.helpers.IntentHelper;
import com.artlite.sqltest.managers.EventManager;
import com.artlite.sqltest.models.user.User;
import com.artlite.sqltest.ui.abs.BaseActivity;

import java.io.InputStream;

/**
 * Class which provide the {@link User} creating
 */
public class CreateUserActivity extends BaseActivity {

    private static final int K_SELECT_PHOTO = 100;
    public static final String K_KEY_USER = "K_KEY_USER";

    @FindViewBy(id = R.id.edit_name)
    private AppCompatEditText editName;
    @FindViewBy(id = R.id.edit_surname)
    private AppCompatEditText editSurname;
    @FindViewBy(id = R.id.edit_about)
    private AppCompatEditText editAbout;
    @FindViewBy(id = R.id.imageAvatar)
    private ImageView imageAvatar;
    private User user;
    private Bitmap avatar;

    /**
     * Method which provide the getting of the layout ID for the current Activity
     *
     * @return layout ID for the current Activity
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_user;
    }

    /**
     * Method which provide the action when Activity is created
     */
    @Override
    protected void onCreateActivity(Bundle bundle) {
        AdapteredInjector.inject(this);
        setOnClickListeners(imageAvatar);
        onInitUser(bundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(K_KEY_USER, user);
        super.onSaveInstanceState(bundle);
    }

    /**
     * Method which provide the {@link User} initializing
     */
    protected final void onInitUser(Bundle bundle) {
        final String methodName = "void onInitUser(bundle)";
        try {
            user = bundle.getParcelable(K_KEY_USER);
        } catch (Exception ex) {
            SQLogHelper.log(this, methodName, ex, null);
        }
        if (user != null) {
            editName.setText(user.getName());
            editSurname.setText(user.getSurname());
            editAbout.setText(user.getAboutMe());
            avatar = user.getAvatar();
            imageAvatar.setImageBitmap(avatar);
        } else {
            editName.setText(SQRandomHelper.generateSentence(this, 1));
            editSurname.setText(SQRandomHelper.generateSentence(this, 1));
            editAbout.setText(SQRandomHelper.generateSentence(this, 50));
        }
    }

    /**
     * Method which provide the getting of the menu ID
     * (Can be as NONE_MENU)
     *
     * @return current menu ID
     */
    @Override
    protected int getMenuId() {
        return R.menu.menu_create_user;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_user: {
                createOrUpdate();
                return true;
            }
            case R.id.menu_item_add_image: {
                pickAvatar();
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Overriden method for the OnClickListener
     *
     * @param v current view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageAvatar: {
                pickAvatar();
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Method which provide the user creating and saving into the
     * {@link com.artlite.sqlib.core.SQDatabase}
     */
    private void createOrUpdate() {
        if (validate(editName, editSurname, editAbout)) {
            if (user == null) {
                final User user = new User(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editAbout.getText().toString(),
                        avatar);
                SQDatabase.insert(user);
            } else {
                user.setName(editName.getText().toString());
                user.setSurname(editSurname.getText().toString());
                user.setAboutMe(editAbout.getText().toString());
                user.setAvatar(avatar);
                SQDatabase.update(user);
            }
            EventManager.send(MainActivity.class, EventCodes.K_CREATE_USER);
            onBackPressed();
        }
    }

    //==============================================================================================
    //                                      PICK IMAGE
    //==============================================================================================

    /**
     * Method which provide the picking of the avatar
     */
    private void pickAvatar() {
        startActivityForResult(IntentHelper.pickImage(), K_SELECT_PHOTO);
    }

    /**
     * Method which provide the action when activity return result
     *
     * @param data current intent
     */
    @Override
    protected void onActivityResult(int requestCode, Intent data) {
        final String methodName = "void onActivityResult(requestCode, data)";
        if (requestCode == K_SELECT_PHOTO) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                avatar = BitmapFactory.decodeStream(imageStream);
                imageAvatar.setImageBitmap(avatar);
            } catch (Exception ex) {
                SQLogHelper.log(this, methodName, ex, null);
            }
        }
    }
}
