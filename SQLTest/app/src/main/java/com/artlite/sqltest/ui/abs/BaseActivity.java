package com.artlite.sqltest.ui.abs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.artlite.sqltest.R;
import com.artlite.sqltest.managers.EventManager;

/**
 * Created by dlernatovich on 2/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected static final int ON_BASE_ACTIVITY_RESULTS = 0x1;
    protected static final String ON_RESULT_EXTRA_KEY = "ON_RESULT_EXTRA_KEY";

    /**
     * Interface which provide the doing some action inside the Handler thread
     */
    protected interface OnActionPerformer {
        void onActionPerform();
    }

    protected static final int NONE_MENU = Integer.MIN_VALUE;
    protected final Handler MAIN_THREAD_HANDLER = new Handler();
    private EventManager.OnEventCallback eventCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onEventRegister();
        onCreateActivity();
    }

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
     */
    protected void startActivity(Class activtyClass) {
        startActivity(new Intent(this, activtyClass));
    }

    //====================ACTIVITY FOR RESULT METHODS====================

    /**
     * Method which provide starting the Activity for results
     *
     * @param activtyClass activity which should be starting
     */
    protected void startActivityForResults(Class activtyClass) {
        startActivityForResult(new Intent(this, activtyClass), ON_BASE_ACTIVITY_RESULTS);
    }

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
     */
    protected void startService(Class serviceClass) {
        if (!isMyServiceRunning(serviceClass)) {
            startService(new Intent(this, serviceClass));
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
    protected void runBackground(final OnActionPerformer actionPerformer) {
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
    protected void runMain(final OnActionPerformer actionPerformer) {
        runMain(0, actionPerformer);
    }

    /**
     * Method which provide the doing action on UI thread after the delaying time
     *
     * @param delayTime       delaying time (in seconds)
     * @param actionPerformer current action
     */
    protected void runMain(double delayTime, final OnActionPerformer actionPerformer) {
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
    protected boolean validateFields(@Nullable final EditText... editTexts) {
        if (editTexts != null) {
            for (final EditText editText : editTexts) {
                if (editText != null) {
                    final String value = editText.getText().toString().trim();
                    if ((value == null) || (value.isEmpty())) {
                        setErrorToEditText(editText,
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
    protected void setErrorToEditText(@Nullable final EditText view,
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
     * Method which provide the registering of the
     * {@link com.artlite.sqltest.managers.EventManager.Event}
     */
    private void onEventRegister() {
        eventCallback = new EventManager.OnEventCallback() {
            @Override
            public void onEvent(@NonNull EventManager.Event event) {
                onEventReceived(event);
            }
        };
        EventManager.register(this, eventCallback);
    }

    /**
     * Method which provide the action when {@link com.artlite.sqltest.managers.EventManager.Event}
     * received
     *
     * @param event instance of {@link com.artlite.sqltest.managers.EventManager.Event}
     */
    protected void onEventReceived(@NonNull final EventManager.Event event) {

    }

    //==========================ABSTRACT METHODS==============================

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
    protected abstract void onCreateActivity();

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
