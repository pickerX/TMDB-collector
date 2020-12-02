package com.pickerx.scache

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.pickerx.scache.internal.SRCache
import com.pickerx.scache.internal.SSRCache

/**
 * Cache factory
 *
 * Get single instance of [SCache] implementor
 */
class Caches {
    class MemoryCache {
        companion object {
            val cache: SCache = SSRCache()
        }
    }

    class IOCache {
        companion object {
            internal val cache: SRCache = SRCache()
            private var inited = false

            fun init(context: Context) {
                if (!inited) {
                    cache.init(context)
                    inited = true
                }
            }
        }
    }

    companion object {
        fun get(): SCache {
            return MemoryCache.cache
        }

        fun io(context: Context): SCache {
            IOCache.init(context)
            return IOCache.cache
        }
    }
}

