package io.tmdb.collector.utils

import android.content.Context
import com.pickerx.scache.Caches

class UserSp {
    companion object {

        fun setLoginAccount(context: Context, account: String) {
            Caches.io(context).put("login_account", account)
        }

        fun getLoginAccount(context: Context): String {
            return Caches.io(context).getString("login_account")
        }

        fun isLogin(context: Context) = getLoginAccount(context).isNotEmpty()
    }
}