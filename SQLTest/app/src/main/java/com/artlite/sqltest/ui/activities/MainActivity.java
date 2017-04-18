package com.artlite.sqltest.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.artlite.adapteredrecyclerview.anotations.FindViewBy;
import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredBaseCallback;
import com.artlite.adapteredrecyclerview.callbacks.OnAdapteredRefreshCallback;
import com.artlite.adapteredrecyclerview.core.AdapteredView;
import com.artlite.adapteredrecyclerview.events.RecycleEvent;
import com.artlite.adapteredrecyclerview.helpers.AdapteredInjector;
import com.artlite.sqlib.callbacks.SQCursorCallback;
import com.artlite.sqlib.constants.SQFilterCompare;
import com.artlite.sqlib.constants.SQFilterDelimiter;
import com.artlite.sqlib.core.SQDatabase;
import com.artlite.sqlib.log.SQLogHelper;
import com.artlite.sqlib.model.SQFilter;
import com.artlite.sqltest.R;
import com.artlite.sqltest.constants.EventCodes;
import com.artlite.sqltest.managers.EventManager;
import com.artlite.sqltest.managers.ThreadManager;
import com.artlite.sqltest.models.user.User;
import com.artlite.sqltest.providers.UserListProvider;
import com.artlite.sqltest.ui.abs.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which provide the created {@link User}
 */
public class MainActivity extends BaseActivity {

    @FindViewBy(id = R.id.recycler_view)
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
            case R.id.menu_item_create_user_thread: {
                createThreadUsers();
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
    protected void onCreateActivity(Bundle bundle) {
        setTitle(R.string.text_users);
        AdapteredInjector.inject(this);
        customizeRecycler(adapteredView);
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
            startActivity(CreateUserActivity.class, new OnStartActivityCallback() {
                @Override
                public void onPreExecute(@NonNull Intent intent) {
                    intent.putExtra(CreateUserActivity.K_KEY_USER, user);
                }
            });
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
                onRecycleDelete(user);
            } else if (recycleEvent.equals(User.K_ADD_TO_FAVORITE)) {
                onRecycleFavorite(user);
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
        ThreadManager.execute(new ThreadManager.OnExecutionCallback() {
            @Override
            public void onBackground() {
                users.addAll(SQDatabase.select(User.class, new SQCursorCallback<User>() {
                            @Nullable
                            @Override
                            public User convert(@NonNull Cursor cursor) {
                                return new User(cursor);
                            }
                        }));
            }

            @Override
            public void onMain() {
                if (adapteredView != null) {
                    adapteredView.set(users);
                    adapteredView.hideRefresh();
                }
            }
        });
    }

    /**
     * Method which provide the event performing
     *
     * @param event instance of {@link EventManager.Event}
     */
    @Override
    public void onEvent(@NonNull EventManager.Event event) {
        super.onEvent(event);
        if (event.getCode() == EventCodes.K_CREATE_USER) {
            receiveUsers();
        }
    }

    //==============================================================================================
    //                                      RECYCLE EVENTS
    //==============================================================================================

    /**
     * Method which provide the deleting user
     *
     * @param user instance of {@link User}
     */
    private void onRecycleDelete(@NonNull final User user) {
        adapteredView.delete(user);
        SQDatabase.delete(user);
    }

    /**
     * Method which provide the add user to favorite
     *
     * @param user instance of {@link User}
     */
    private void onRecycleFavorite(@NonNull final User user) {
        user.switchFavorite();
        SQDatabase.update(user);
        adapteredView.update(user);
        adapteredView.sort();
    }

    //==============================================================================================
    //                                       TESTING
    //==============================================================================================

    protected void createThreadUsers() {
        final List<User> users = new UserListProvider().get(this);
        final Toast toast1 = Toast.makeText(this, "Start task 1", Toast.LENGTH_SHORT);
        final Toast toast1Finish = Toast.makeText(this, "Finish task 1", Toast.LENGTH_SHORT);
        final Toast toast2 = Toast.makeText(this, "Start task 2", Toast.LENGTH_SHORT);
        final Toast toast2Finish = Toast.makeText(this, "Finish task 2", Toast.LENGTH_SHORT);
        final AsyncTask<Void, Void, Void> task1 = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                toast1.show();
                SQDatabase.insert(users);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                toast1Finish.show();
                super.onPostExecute(aVoid);
            }
        };

        final AsyncTask<Void, Void, Void> task2 = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                toast2.show();
                SQDatabase.insert(users);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                toast2Finish.show();
                super.onPostExecute(aVoid);
            }
        };

        task1.execute();
        task2.execute();
    }


}
