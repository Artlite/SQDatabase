package com.artlite.sqltest.ui.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredBaseCallback;
import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredRefreshCallback;
import com.artlite.adapteredrecyclerview.core.AdapteredView;
import com.artlite.adapteredrecyclerview.events.RecycleEvent;
import com.artlite.sqltest.R;
import com.artlite.sqltest.models.User;
import com.artlite.sqltest.ui.abs.BaseActivity;

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
        adapteredView = (AdapteredView) findViewById(R.id.recycler_view);
        customizeRecycler(adapteredView);
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
        }

        @Override
        public void onItemLongClick(int index,
                                    @NonNull final User user) {

        }

        @Override
        public void onActionReceived(@NonNull final RecycleEvent recycleEvent,
                                     int index,
                                     @NonNull final User baseObject) {
            if (recycleEvent.equals(User.K_DELETE_USER)) {
                adapteredView.delete(baseObject);
            }
        }
    };

    /**
     * Class which provide the refresh callback
     */
    private final OnAdapteredRefreshCallback refreshCallback = new OnAdapteredRefreshCallback() {
        @Override
        public void onRefreshData() {
            adapteredView.hideRefresh();
        }
    };

}
