package com.pickerx.scache.internal

import android.util.Log
import com.pickerx.scache.SCache
import com.pickerx.scache.hash

/**
 * Memory cache only
 */
internal class SSRCache(initialCapacity: Int = 0) : SCache {

    private val mCache: SSRArray = SSRArray(initialCapacity)

    override fun getInt(key: String, defaultValue: Int): Int {
        return get(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return get(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return get(key, defaultValue)
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return get(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return get(key, defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String {
        return get(key, defaultValue)
    }

    override fun <T> getArray(key: String): List<T> {
        val array = get(key, emptyList<T>())
        return array
    }

    override fun <T> get(key: String): T? {
        return get(key, null)
    }

    fun <T> get(key: String, defaultValue: T): T {
        val realKey = key.hash()
        val value = mCache[realKey, defaultValue]

        return value ?: defaultValue
    }

    override fun <T> put(key: String, value: T, keeping: Long) {
        val realKey = key.hash()
        mCache.put(realKey, value)
        if (keeping > 0) {
            Log.w(
                "SCache",
                "Memory cache isn't support timeout mechanism, if you need this, let's me know by creating issue"
            )
        }
    }

    override fun clear() = mCache.clear()

    override fun size(): Int = mCache.size()

    override fun contain(key: String): Boolean = mCache.containsKey(key.hash())

    override fun remove(key: String): Boolean = mCache.remove(key.hash())

}