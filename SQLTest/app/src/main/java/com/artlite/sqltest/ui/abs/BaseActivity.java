package com.artlite.sqltest.ui.abs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.artlite.sqlib.log.SQLogHelper;
import com.artlite.sqltest.R;
import com.artlite.sqltest.managers.EventManager;

/**
 * Created by dlernatovich on 2/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,
        EventManager.OnEventCallback {
    private static final String TAG = BaseActivity.class.getName();
    protected static final int ON_BASE_ACTIVITY_RESULTS = 0x1;
    protected static final String ON_RESULT_EXTRA_KEY = "ON_RESULT_EXTRA_KEY";
    protected static final int NONE_MENU = Integer.MIN_VALUE;
    protected final Handler MAIN_THREAD_HANDLER = new Handler();

    /**
     * Method which provide the action when {@link android.app.Activity} is created
     *
     * @param bundle instance of {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutId());
        EventManager.register(this, this);
        onCreateActivity((bundle == null) ? getIntent().getExtras() : bundle);
    }

    /**
     * Method which provide the create of the option menu
     *
     * @param menu instance of {@link Menu}
     * @return creating result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = getMenuId();
        if (menuId == NONE_MENU) {
            return false;
        }
        getMenuInflater().inflate(menuId, menu);
        return true;
    }

    /**
     * Overriden method for the OnClickListener
     *
     * @param v current view
     */
    @Override
    public void onClick(View v) {

    }


    /**
     * Method which provide the replace of the fragment inside the activity
     *
     * @param fragment     current fragment
     * @param container_id current container id
     */
    public void replace(Fragment fragment, int container_id) {
        getFragmentTransaction().replace(container_id, fragment,
                getFragmentTag()).commit();
    }


    /**
     * Method which provide the getting of the FragmentTransaction
     *
     * @return current FragmentTransaction
     */
    private FragmentTransaction getFragmentTransaction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        return ft;
    }

    /**
     * Method which provide the getting of the fragment tag
     *
     * @return current fragment Tag
     */
    private String getFragmentTag() {
        return getClass().getName();
    }

    /**
     * Method which provide starting the Activity
     *
     * @param activtyClass activity which should be starting
     * @param callbacks    instances of {@link OnStartActivityCallback}
     */
    protected void startActivity(Class activtyClass,
                                 @Nullable final OnStartActivityCallback... callbacks) {
        final Intent intent = new Intent(this, activtyClass);
        if ((callbacks != null) && (callbacks.length > 0)) {
            for (final OnStartActivityCallback callback : callbacks) {
                if (callback != null) {
                    callback.onPreExecute(intent);
                }
            }
        }
        startActivity(intent);
    }

    //====================ACTIVITY FOR RESULT METHODS====================

    /**
     * Method which provide starting the Activity for results
     *
     * @param activtyClass activity which should be starting
     * @param callbacks    instances of {@link OnStartActivityCallback}
     */
    protected void startActivityForResults(Class activtyClass,
                                           @Nullable final OnStartActivityCallback... callbacks) {
        final Intent intent = new Intent(this, activtyClass);
        if ((callbacks != null) && (callbacks.length > 0)) {
            for (final OnStartActivityCallback callback : callbacks) {
                if (callback != null) {
                    callback.onPreExecute(intent);
                }
            }
        }
        startActivityForResult(intent, ON_BASE_ACTIVITY_RESULTS);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode == RESULT_OK) {
            // The user picked a contact.
            // The Intent's data Uri identifies which contact was selected.
            onActivityResult(requestCode, data);
        }

    }

    /**
     * Method which provide the action when activity return result
     *
     * @param data current intent
     */
    protected void onActivityResult(int requestCode, Intent data) {

    }

    /**
     * Method which provide the sending of the Activity results
     *
     * @param extraValue current extra value
     */
    protected void sendActivityResult(String extraValue) {
        if (extraValue == null) {
            extraValue = "";
        }
        Intent intent = new Intent();
        intent.putExtra(ON_RESULT_EXTRA_KEY, extraValue);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    /**
     * Method which provide starting the Service
     *
     * @param serviceClass service which should be starting
     * @param callbacks    instances of {@link OnStartActivityCallback}
     */
    protected void startService(Class serviceClass,
                                @Nullable final OnStartActivityCallback... callbacks) {
        if (!isMyServiceRunning(serviceClass)) {
            final Intent intent = new Intent(this, serviceClass);
            if ((callbacks != null) && (callbacks.length > 0)) {
                for (final OnStartActivityCallback callback : callbacks) {
                    if (callback != null) {
                        callback.onPreExecute(intent);
                    }
                }
            }
            startService(intent);
        }
    }

    /**
     * Method which provide the service running checking
     *
     * @param serviceClass current service
     * @return checking results
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method which provide the setting of the OnClickListener
     *
     * @param views current list of views
     */
    protected void setOnClickListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    /**
     * Method which provide the doing action on UI thread after the delaying time
     *
     * @param actionPerformer current action
     */
    protected void onBackground(final OnActionPerformer actionPerformer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                actionPerformer.onActionPerform();
            }
        }).start();
    }

    /**
     * Method which provide the doing action on UI thread after the delaying time
     *
     * @param actionPerformer current action
     */
    protected void onMain(final OnActionPerformer actionPerformer) {
        onMain(0, actionPerformer);
    }

    /**
     * Method which provide the doing action on UI thread after the delaying time
     *
     * @param delayTime       delaying time (in seconds)
     * @param actionPerformer current action
     */
    protected void onMain(double delayTime, final OnActionPerformer actionPerformer) {
        MAIN_THREAD_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                actionPerformer.onActionPerform();
            }
        }, (int) (delayTime * 1000));
    }

    /**
     * Method which provide the validate of {@link EditText}
     *
     * @param editTexts list of {@link EditText}
     * @return checking value
     */
    protected boolean validate(@Nullable final EditText... editTexts) {
        if (editTexts != null) {
            for (final EditText editText : editTexts) {
                if (editText != null) {
                    final String value = editText.getText().toString().trim();
                    if ((value == null) || (value.isEmpty())) {
                        setError(editText,
                                getResources().getString(R.string.text_required_field));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method which provide the set error to EditText
     *
     * @param view      current EditText
     * @param errorText error text
     */
    protected void setError(@Nullable final EditText view,
                            @StringRes int errorText) {
        try {
            setError(view, getResources().getString(errorText));
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    /**
     * Method which provide the set error to EditText
     *
     * @param view      current EditText
     * @param errorText error text
     */
    protected void setError(@Nullable final EditText view,
                            @Nullable final String errorText) {
        if ((view != null)) {
            view.setError(errorText);
            view.requestFocus();
        }
    }

    //==============================================================================================
    //                                          EVENTS
    //==============================================================================================

    /**
     * Method which provide the event performing
     *
     * @param event instance of {@link EventManager.Event}
     */
    @Override
    public void onEvent(@NonNull final EventManager.Event event) {
        SQLogHelper.log(this, "public void onEvent(event)", null, event);
    }

    //==============================================================================================
    //                                      ABSTRACT METHODS
    //==============================================================================================

    /**
     * Method which provide the getting of the layout ID for the current Activity
     *
     * @return layout ID for the current Activity
     */
    protected abstract int getLayoutId();

    /**
     * Method which provide the getting of the menu ID
     * (Can be as NONE_MENU)
     *
     * @return current menu ID
     */
    protected int getMenuId() {
        return NONE_MENU;
    }

    /**
     * Method which provide the action when Activity is created
     */
    protected abstract void onCreateActivity(Bundle bundle);

    //==============================================================================================
    //                                          CALLBACKS
    //==============================================================================================

    /**
     * Callback which provide the action with intent before start {@link android.app.Activity}
     */
    protected interface OnStartActivityCallback {
        /**
         * Method which provide the action before starting activity
         *
         * @param intent instance of {@link Intent}
         */
        void onPreExecute(@NonNull final Intent intent);
    }

    /**
     * Interface which provide the doing some action inside the Handler thread
     */
    protected interface OnActionPerformer {
        /**
         * Method which provide the action perform
         */
        void onActionPerform();
    }

    //TODO Example for the onOptionsItemSelected
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        // Handle action bar item clicks here. The action bar will
    //        // automatically handle clicks on the Home/Up button, so long
    //        // as you specify a parent activity in AndroidManifest.xml.
    //        int id = item.getItemId();
    //
    //        //noinspection SimplifiableIfStatement
    //        if (id == R.id.action_settings) {
    //            return true;
    //        }
    //
    //        return super.onOptionsItemSelected(item);
    //    }

}
