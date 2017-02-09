package com.artlite.sqltest.ui.activities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredBaseCallback;
import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredRefreshCallback;
import com.artlite.adapteredrecyclerview.core.AdapteredView;
import com.artlite.adapteredrecyclerview.events.RecycleEvent;
import com.artlite.sqlib.callbacks.SQCursorCallback;
import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqlib.log.SQLogHelper;
import com.artlite.sqltest.R;
import com.artlite.sqltest.managers.TransferManager;
import com.artlite.sqltest.models.User;
import com.artlite.sqltest.ui.abs.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private AdapteredView adapteredView;

    /**
     * Method which provide the getting of the layout ID for the current Activity
     *
     * @return layout ID for the current Activity
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * Method which provide the getting of the menu ID
     * (Can be as NONE_MENU)
     *
     * @return current menu ID
     */
    @Override
    protected int getMenuId() {
        return R.menu.menu_main_activity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_create_user: {
                startActivity(CreateUserActivity.class);
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which provide the action when Activity is created
     */
    @Override
    protected void onCreateActivity() {
        setTitle(R.string.text_users);
        adapteredView = (AdapteredView) findViewById(R.id.recycler_view);
        customizeRecycler(adapteredView);
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        receiveUsers();
    }

    /**
     * Method which provide the customization of the {@link AdapteredView}
     *
     * @param adapteredView instance of {@link AdapteredView}
     */
    protected void customizeRecycler(@NonNull final AdapteredView adapteredView) {
        adapteredView.init(new LinearLayoutManager(this), callback, refreshCallback);
        adapteredView.setIsNeedResfresh(true);
    }

    /**
     * Class which provide the action when user click something inside the {@link AdapteredView}
     */
    private final OnAdapteredBaseCallback callback = new OnAdapteredBaseCallback<User>() {
        @Override
        public void onItemClick(int index,
                                @NonNull final User user) {
            SQLogHelper.log(MainActivity.this, "onItemClick", null, user);
            TransferManager.getInstance().put(CreateUserActivity.class, user);
            startActivity(CreateUserActivity.class);
        }

        @Override
        public void onItemLongClick(int index,
                                    @NonNull final User user) {
            SQLogHelper.log(MainActivity.this, "onItemLongClick", null, user);
        }

        @Override
        public void onActionReceived(@NonNull final RecycleEvent recycleEvent,
                                     int index,
                                     @NonNull final User user) {
            if (recycleEvent.equals(User.K_DELETE_USER)) {
                SQDatabase.delete(user);
                adapteredView.delete(user);
            }
        }
    };

    /**
     * Class which provide the refresh callback
     */
    private final OnAdapteredRefreshCallback refreshCallback = new OnAdapteredRefreshCallback() {
        @Override
        public void onRefreshData() {
            receiveUsers();
        }
    };

    /**
     * Method which provide the getting of list of users
     */
    protected final void receiveUsers() {
        final List<User> users = new ArrayList<>();
        adapteredView.showRefresh();
        runBackground(new OnActionPerformer() {
            @Override
            public void onActionPerform() {
                users.addAll(SQDatabase.select(User.class, new SQCursorCallback<User>() {
                    @Nullable
                    @Override
                    public User convert(@NonNull Cursor cursor) {
                        return new User(cursor);
                    }
                }));
                runMain(new OnActionPerformer() {
                    @Override
                    public void onActionPerform() {
                        if (adapteredView != null) {
                            adapteredView.set(users);
                            adapteredView.hideRefresh();
                        }
                    }
                });
            }
        });
    }

}