package com.artlite.sqltest.providers;

/**
 * Class which provide the base functional for providers
 */
public interface BaseProvider<T, K> {
    /**
     * Method which provide the getting of the required objects
     *
     * @param parameters list of parameters
     * @return instance of required {@link Object}
     */
    T get(K... parameters);
}
