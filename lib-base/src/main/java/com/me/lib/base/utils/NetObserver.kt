package com.me.lib.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * 网络状态监听器，绑定页面
 *
 * ClassName:NetObserver
 * Package:com.me.lib.base.utils
 * Description:
 * @date:2020/10/12 9:34 AM
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetObserver(private val context: Context) {

    companion object {
        fun get(context: Context): NetObserver {
            return NetObserver(context)
        }
    }

    private val mCallback = object :
        ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            mObservers.forEach { it.invoke() }
        }
    }

    init {
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder()
            .build()
        manager.registerNetworkCallback(request, mCallback)
    }

    private val mObservers = ArrayList<() -> Unit>()

    fun observer(available: () -> Unit) {
        mObservers.add(available)
    }

    fun destroy() {
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.unregisterNetworkCallback(mCallback)
    }
}