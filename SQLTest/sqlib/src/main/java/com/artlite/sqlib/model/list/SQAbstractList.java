package com.artlite.sqlib.model.list;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by dlernatovich on 3/9/2017.
 */

abstract class SQAbstractList<T> extends SQList<T> {

    /**
     * Default constructor
     */
    protected SQAbstractList() {
        super();
    }

    /**
     * Constructor which provide the create {@link SQLoaderList} from {@link Parcel}
     *
     * @param parcel instance of {@link Parcel}
     */
    protected SQAbstractList(Parcel parcel) {
        super(parcel);
    }

    /**
     * Method which provide the getting of the objects {@link List} size
     *
     * @return objects list size
     */
    @Override
    public synchronized int size() {
        return objects.size();
    }

    /**
     * Method which provide the checking if objects {@link List} is empty
     *
     * @return checking results
     */
    @Override
    public synchronized boolean isEmpty() {
        return objects.isEmpty();
    }

    /**
     * Method which provide the checking if objects {@link List} is contain {@link Object}
     *
     * @param object instance of {@link Object}
     * @return checking results
     */
    @Override
    public synchronized boolean contains(Object object) {
        return objects.contains(object);
    }

    /**
     * Method which provide the getting of the objects {@link List} {@link Iterator}
     *
     * @return instance of {@link Iterator}
     */
    @NonNull
    @Override
    public synchronized Iterator<T> iterator() {
        return objects.iterator();
    }

    /**
     * Method which provide the converting the objects {@link List} to array
     *
     * @return instance of array
     */
    @NonNull
    @Override
    public synchronized Object[] toArray() {
        return objects.toArray();
    }

    /**
     * Method which provide the converting the objects {@link List} to array
     *
     * @param array array instance
     * @param <T1>  object type
     * @return instance of array
     */
    @NonNull
    @Override
    public synchronized <T1> T1[] toArray(@NonNull T1[] array) {
        return objects.toArray(array);
    }

    /**
     * Method which provide the adding object to objects {@link List}
     *
     * @param object instance of {@link Object}
     * @return adding result
     */
    @Override
    public synchronized boolean add(T object) {
        return objects.add(object);
    }

    /**
     * Method which provide th removing object from objects {@link List}
     *
     * @param object instance of {@link Object}
     * @return removing results
     */
    @Override
    public synchronized boolean remove(Object object) {
        return objects.remove(object);
    }

    /**
     * Method which provide the checking if objects {@link List} contain
     * {@link java.util.Collections}
     *
     * @param collection instance of {@link java.util.Collections}
     * @return checking result
     */
    @Override
    public synchronized boolean containsAll(@NonNull Collection<?> collection) {
        return objects.containsAll(collection);
    }

    /**
     * Method which provide the adding {@link java.util.Collections} to objects {@link List}
     *
     * @param collection instance of {@link java.util.Collections}
     * @return adding result
     */
    @Override
    public synchronized boolean addAll(@NonNull Collection<? extends T> collection) {
        return objects.addAll(collection);
    }

    /**
     * Method which provide the adding {@link java.util.Collections} to objects {@link List}
     *
     * @param index      index value
     * @param collection instance of {@link java.util.Collections}
     * @return adding result
     */
    @Override
    public synchronized boolean addAll(int index, @NonNull Collection<? extends T> collection) {
        return objects.addAll(index, collection);
    }

    /**
     * Method which provide the removing {@link java.util.Collections} to objects {@link List}
     *
     * @param collection instance of {@link java.util.Collections}
     * @return removing result
     */
    @Override
    public synchronized boolean removeAll(@NonNull Collection<?> collection) {
        return objects.removeAll(collection);
    }

    /**
     * Method which provide the retaining {@link java.util.Collections} from objects {@link List}
     *
     * @param collection instance of {@link java.util.Collections}
     * @return retaining result
     */
    @Override
    public synchronized boolean retainAll(@NonNull Collection<?> collection) {
        return objects.retainAll(collection);
    }

    /**
     * Method which provide the clearing of the objects {@link List}
     */
    @Override
    public synchronized void clear() {
        objects.clear();
    }

    /**
     * Method which provide the getting {@link Object} by objects {@link List}
     *
     * @param index index value
     * @return instance of {@link Object}
     */
    @Override
    public synchronized T get(int index) {
        return objects.get(index);
    }

    /**
     * Method which provide the setting {@link Object} to objects {@link List}
     *
     * @param index  index value
     * @param object instance of {@link Object}
     * @return instance of {@link Object}
     */
    @Override
    public synchronized T set(int index, T object) {
        return objects.set(index, object);
    }

    /**
     * Method which provide the adding {@link Object} to objects {@link List}
     *
     * @param index  index value
     * @param object instance of {@link Object}
     * @return instance of {@link Object}
     */
    @Override
    public synchronized void add(int index, T object) {
        objects.add(index, object);
    }

    /**
     * Method which provide the removing {@link Object} from objects {@link List}
     *
     * @param index index value
     * @return instance of {@link Object}
     */
    @Override
    public synchronized T remove(int index) {
        return objects.remove(index);
    }

    /**
     * Method which provide getting index of {@link Object} from objects {@link List}
     *
     * @param object instance of {@link Object}
     * @return index for {@link Object}
     */
    @Override
    public synchronized int indexOf(Object object) {
        return objects.indexOf(object);
    }

    /**
     * Method which provide getting last index of {@link Object} from objects {@link List}
     *
     * @param object instance of {@link Object}
     * @return last index for {@link Object}
     */
    @Override
    public synchronized int lastIndexOf(Object object) {
        return objects.lastIndexOf(object);
    }

    /**
     * Method which provide the getting of the {@link ListIterator} fro objects {@link List}
     *
     * @return instance of {@link ListIterator}
     */
    @Override
    public synchronized ListIterator<T> listIterator() {
        return objects.listIterator();
    }

    /**
     * Method which provide the getting of the {@link ListIterator} fro objects
     * {@link List} by index
     *
     * @param index index value
     * @return instance of {@link ListIterator}
     */
    @NonNull
    @Override
    public synchronized ListIterator<T> listIterator(int index) {
        return objects.listIterator(index);
    }

    /**
     * Method which provide the getting of the sub {@link List} from objects {@link List}
     *
     * @param start start index
     * @param end   end index
     * @return instance of {@link List}
     */
    @NonNull
    @Override
    public synchronized List<T> subList(int start, int end) {
        return objects.subList(start, end);
    }
}
