package com.me.lib.base.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat


/**
 *
 * @author: pickerx
 * @date:2021/3/10 12:49 PM
 */
class WindowExt {
    companion object {
        /**
         * 请求全屏窗口
         */
        fun requestFull(activity: AppCompatActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //真正的全屏体验
                if (Build.VERSION.SDK_INT >= 21) {
                    val w = activity.window
                    val decorView = w.decorView
                    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                    w.statusBarColor = Color.TRANSPARENT
                    w.navigationBarColor = Color.TRANSPARENT
                }
                val actionBar: ActionBar? = activity.supportActionBar
                actionBar?.hide()
            }
        }

        /**
         * 修改状态栏颜色
         * 默认透明
         */
        fun setStatusBarColor(
            activity: Activity,
            colorId: Int = android.R.color.transparent
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window
                //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.statusBarColor =
                    ResourcesCompat.getColor(activity.resources, colorId, activity.theme)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val window = activity.window
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            }
        }
    }
}