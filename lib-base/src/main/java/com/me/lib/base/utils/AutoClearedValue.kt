package com.me.lib.base.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw NPE.
 *
 * @param clear release resource by user
 */
class AutoClearedValue<T : Any>(fragment: Fragment, clear: ((T?) -> Unit)? = null) :
    ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                val ownerLiveData = fragment.viewLifecycleOwnerLiveData
                ownerLiveData.observe(fragment, Observer { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object :
                        DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            clear?.invoke(_value)
                            _value = null
                        }
                    })
                })
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)

fun <T : Any> Fragment.autoCleared(clear: (T?) -> Unit) = AutoClearedValue(this, clear)