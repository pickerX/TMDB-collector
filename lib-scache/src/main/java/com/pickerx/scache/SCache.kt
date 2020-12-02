package com.pickerx.scache

/**
 * Super Cache interface for developer
 *
 */
interface SCache {
    /**
     * get the Int data of key
     */
    fun getInt(key: String, defaultValue: Int = 0): Int

    /**
     * get the Long data of key
     */
    fun getLong(key: String, defaultValue: Long = 0): Long

    /**
     * get the Float data of key
     */
    fun getFloat(key: String, defaultValue: Float = 0f): Float

    /**
     * get the Double data of key
     */
    fun getDouble(key: String, defaultValue: Double = 0.0): Double

    /**
     * get the Boolean data of key
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    /**
     * get the String data of key
     */
    fun getString(key: String, defaultValue: String = ""): String

    /**
     * get List<T>, empty list when not found
     *
     * [T] must implement [Serializable] interface
     */
    fun <T> getArray(key: String): List<T>

    /**
     * get the exactly type[T] object, null when not found
     *
     * [T] must implement [Serializable] interface
     */
    fun <T> get(key: String): T?

    // fun <T> getLive(key: String): LiveData<T>
    /**
     * put key-value for caching
     *
     * @param key
     * @param value
     * @param keeping how long keeping the key-value, unit second
     *      default -1:no expire time
     */
    fun <T> put(key: String, value: T, keeping: Long = -1)

    /**
     * clear all cache data
     * release memory
     */
    fun clear()

    /**
     * count of data
     */
    fun size(): Int

    /**
     * check contain the key value or not
     */
    fun contain(key: String): Boolean

    /**
     * remove the certain key-value
     * @return true success false failed
     */
    fun remove(key: String): Boolean
}