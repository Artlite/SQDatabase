package com.artlite.sqlib.model.map;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.sqlib.constants.Constants;
import com.artlite.sqlib.helpers.random.SQRandomHelper;
import com.artlite.sqlib.helpers.validation.SQValidationHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class which provide the {@link Map} functional
 */

public abstract class SQMap<K, V> implements Map<K, V>, Parcelable {

    //==============================================================================================
    //                                      FIELDS
    //==============================================================================================

    /**
     * Map with object
     */
    private final Map<K, V> map;
    private String id;

    //==============================================================================================
    //                                    CONSTRUCTORS
    //==============================================================================================

    /**
     * Constructor which provide the create {@link SQMap}
     */
    public SQMap() {
        map = new HashMap<>();
        id = SQRandomHelper.generateString(Constants.K_ID_LENGTH);
    }

    /**
     * Method which provide the create {@link SQMap} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQMap(Parcel parcel) {
        this.id = parcel.readString();
        int mapSize = parcel.readInt();
        this.map = new HashMap<K, V>();
        for (int i = 0; i < mapSize; i++) {
            K key = readKey(parcel);
            V value = readValue(parcel);
            if (!SQValidationHelper.isNull(key, value)) {
                this.map.put(key, value);
            }
        }
    }

    //==============================================================================================
    //                                      MAP METHODS
    //==============================================================================================

    /**
     * Method which provide the getting of the size of {@link SQMap}
     *
     * @return size of {@link SQMap}
     */
    @Override
    public synchronized int size() {
        return map.size();
    }

    /**
     * Method which provide the checking if {@link SQMap} is empty
     *
     * @return checking result
     */
    @Override
    public synchronized boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Method which provide the checking if {@link SQMap} contain key
     *
     * @param object instance of {@link Object} key
     * @return checking result
     */
    @Override
    public synchronized boolean containsKey(Object object) {
        return map.containsKey(object);
    }

    /**
     * Method which provide the checking if {@link SQMap} contain of the value
     *
     * @param object instance of {@link Object} value
     * @return checking value
     */
    @Override
    public synchronized boolean containsValue(Object object) {
        return map.containsValue(object);
    }

    /**
     * Method which provide the getting {@link Object} value by key
     *
     * @param key instance of {@link Object} key
     * @return object value
     */
    @Nullable
    @Override
    public synchronized V get(Object key) {
        return map.get(key);
    }

    /**
     * Method which provide the putting {@link Object} key and {@link Object} value
     *
     * @param key   instance of {@link Object}
     * @param value instance of {@link Object}
     * @return putting value
     */
    @Override
    public synchronized V put(K key, V value) {
        return map.put(key, value);
    }

    /**
     * Method which provide the removing {@link Object} value by key {@link Object}
     *
     * @param object instance of {@link Object} key
     * @return removing result
     */
    @Override
    public synchronized V remove(Object object) {
        return map.remove(object);
    }

    /**
     * Method which provide the putting {@link Map} to {@link SQMap}
     *
     * @param map instance of {@link Map}
     */
    @Override
    public synchronized void putAll(@NonNull Map<? extends K, ? extends V> map) {
        this.map.putAll(map);
    }

    /**
     * Method which provide the clearing of the {@link SQMap}
     */
    @Override
    public synchronized void clear() {
        map.clear();
    }

    /**
     * Method which provide the getting of the key {@link Set}
     *
     * @return instance of key {@link Set}
     */
    @NonNull
    @Override
    public synchronized Set<K> keySet() {
        return map.keySet();
    }

    /**
     * Method which provide the getting of the {@link Collection}
     *
     * @return instance of {@link Collection}
     */
    @NonNull
    @Override
    public synchronized Collection<V> values() {
        return map.values();
    }

    /**
     * Method which provide the getting of the {@link Entry} of {@link Set}
     *
     * @return instance of {@link Set} of the {@link Entry}
     */
    @NonNull
    @Override
    public synchronized Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    //==============================================================================================
    //                                    PARCELABLE
    //==============================================================================================

    /**
     * Method which provide the describing of the {@link SQMap}
     *
     * @return describing of the {@link SQMap}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method which provide the write {@link SQMap} to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeInt(this.map.size());
        for (Entry<K, V> entry : this.map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (!SQValidationHelper.isNull(key, value)) {
                writeKey(parcel, flags, key);
                writeValue(parcel, flags, value);
            }
        }
    }

    //==============================================================================================
    //                                    EQUALS/HASH CODE
    //==============================================================================================

    /**
     * Method which provide the equaling of the {@link SQMap} objects
     *
     * @param object instance of {@link Object}
     * @return equaling result
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SQMap<?, ?> sqMap = (SQMap<?, ?>) object;
        return id.equals(sqMap.id);
    }

    /**
     * Method which provide the generating of the hash code for the {@link SQMap}
     *
     * @return hash code for the {@link SQMap}
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }


    //==============================================================================================
    //                                    ABSTRACT METHODS
    //==============================================================================================

    /**
     * Method which provide the writing {@link SQMap} key to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     * @param key    instance of {@link Object}
     */
    protected abstract void writeKey(@NonNull final Parcel parcel, int flags, @NonNull final K key);

    /**
     * Method which provide the writing {@link SQMap} value to {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @param flags  flags value
     * @param value  instance of {@link Object}
     */
    protected abstract void writeValue(@NonNull final Parcel parcel, int flags, @NonNull final V value);

    /**
     * Method which provide the reading key {@link Object} from the {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @return object
     */
    @Nullable
    protected abstract K readKey(@NonNull final Parcel parcel);

    /**
     * Method which provide the reading value {@link Object} from the {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     * @return object
     */
    @Nullable
    protected abstract V readValue(@NonNull final Parcel parcel);
}
