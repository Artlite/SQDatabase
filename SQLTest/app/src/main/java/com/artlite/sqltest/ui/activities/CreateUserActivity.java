package com.artlite.sqltest.ui.activities;

import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;

import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqltest.R;
import com.artlite.sqltest.constants.EventCodes;
import com.artlite.sqltest.helpers.RandomHelper;
import com.artlite.sqltest.managers.EventManager;
import com.artlite.sqltest.managers.TransferManager;
import com.artlite.sqltest.models.User;
import com.artlite.sqltest.ui.abs.BaseActivity;

/**
 * Class which provide the {@link User} creating
 */
public class CreateUserActivity extends BaseActivity {

    private AppCompatEditText editName;
    private AppCompatEditText editSurname;
    private AppCompatEditText editAbout;
    private User user;

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
    protected void onCreateActivity() {
        editName = (AppCompatEditText) findViewById(R.id.edit_name);
        editSurname = (AppCompatEditText) findViewById(R.id.edit_surname);
        editAbout = (AppCompatEditText) findViewById(R.id.edit_about);
        onInitUser();
    }

    /**
     * Method which provide the {@link User} initializing
     */
    protected final void onInitUser() {
        user = TransferManager.getInstance().get(this);
        if (user != null) {
            editName.setText(user.getName());
            editSurname.setText(user.getSurname());
            editAbout.setText(user.getAboutMe());
        } else {
            editName.setText(RandomHelper.generateRandomString(10));
            editSurname.setText(RandomHelper.generateRandomString(6));
            editAbout.setText(RandomHelper.generateRandomString(200));
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which provide the user creating and saving into the
     * {@link com.artlite.sqlib.core.SQDatabase}
     */
    private void createOrUpdate() {
        if (validateFields(editName, editSurname, editAbout)) {
            if (user == null) {
                final User user = new User(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editAbout.getText().toString());
                SQDatabase.insert(user);
            } else {
                user.setName(editName.getText().toString());
                user.setSurname(editSurname.getText().toString());
                user.setAboutMe(editAbout.getText().toString());
                SQDatabase.update(user);
            }
            EventManager.send(MainActivity.class, EventCodes.K_CREATE_USER);
            onBackPressed();
        }
    }
}
