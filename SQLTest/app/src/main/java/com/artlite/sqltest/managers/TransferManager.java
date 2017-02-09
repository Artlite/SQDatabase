package com.artlite.sqltest.managers;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.artlite.sqlib.log.SQLogHelper;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artli on 10.02.2017.
 */

public final class TransferManager {

    private static TransferManager instance;
    private final Map<Class, WeakReference<Object>> data = new HashMap<>();

    /**
     * Method which provide the instance of the {@link TransferManager}
     *
     * @return instance of the {@link TransferManager}
     */
    public static TransferManager getInstance() {
        if (instance == null) {
            instance = new TransferManager();
        }
        return instance;
    }

    /**
     * Default constructor
     */
    private TransferManager() {

    }

    /**
     * Method which provide the putting object for transfert
     *
     * @param dest   destination class
     * @param object instance of {@link Object}
     * @return put results
     */
    public boolean put(@Nullable final Class dest,
                       @Nullable final Object object) {
        final String methodName = "put(aClass, object)";
        boolean result = true;
        try {
            data.put(dest, new WeakReference<Object>(object));
        } catch (Exception ex) {
            SQLogHelper.log(this, methodName, ex, object);
        }
        return result;
    }

    /**
     * Method which provide the getting of the object
     *
     * @param owner from owner
     * @param <T>   object type
     * @return instance of {@link Object}
     */
    @Nullable
    public <T extends Object> T get(@Nullable final Object owner) {
        if (owner != null) {
            return get(owner.getClass());
        }
        return null;
    }

    /**
     * Method which provide the getting of the object
     *
     * @param owner from owner
     * @param <T>   object type
     * @return instance of {@link Object}
     */
    @Nullable
    public <T extends Object> T get(@Nullable final Class owner) {
        final String methodName = "T get(owner)";
        if (owner != null) {
            if (data.containsKey(owner)) {
                final WeakReference<Object> reference = data.get(owner);
                data.remove(owner);
                if ((reference != null) && (reference.get() != null)) {
                    final Object object = reference.get();
                    try {
                        return (T) object;
                    } catch (Exception ex) {
                        SQLogHelper.log(this, methodName, ex, owner);
                    }
                }
            }
        }
        return null;
    }
}
