package com.me.lib.base.security

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.me.lib.base.utils.ATLEAST_M

/**
 * Permission Request, use in exactly Activity.
 *
 * Base Activity not recommend
 */
interface PermissionRequest {

    fun request()

    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )

    interface Callback {

        fun getActivity(): Activity

        /**
         * permission refuse by user
         */
        fun onRefused(index: Int, permission: String)

        /**
         * allow permission
         */
        fun onAllowed(index: Int, permission: String)
    }

    /* empty implement for override */
    abstract class DefaultCallback : Callback {

        override fun onAllowed(index: Int, permission: String) {}

        override fun onRefused(index: Int, permission: String) {}
    }

    class Manager(private val cb: Callback) : PermissionRequest {

        private val requestCode = 0x123

        @TargetApi(Build.VERSION_CODES.M)
        override fun request() {
            val permissions = cb.javaClass.getAnnotation(Permission::class.java)?.value
            // no annotation, skip
            if (permissions == null) {
                Log.i("PermissionManager", "No permission request")
                return
            }

            val activity = cb.getActivity()

            if (ATLEAST_M) {
                permissions.first { activity.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
                    .apply {
                        if (!TextUtils.isEmpty(this))
                            activity.requestPermissions(permissions, requestCode)
                    }
            }
        }

        override fun onRequestPermissionResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            grantResults.forEachIndexed { index, value ->
                if (value == PackageManager.PERMISSION_GRANTED) {
                    Log.v(javaClass.simpleName, "requestCode:$requestCode")
                    cb.onRefused(index, permissions[index])
                }
            }
        }
    }
}
