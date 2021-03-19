package com.me.lib.base.utils

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStream

val ATLEAST_M = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
val ATLEAST_N = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
val ATLEAST_P = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun AppCompatActivity.isNotGranted(permission: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED

/**
 * check permission
 */
fun AppCompatActivity.checkAndRequestPermission(
    permissions: Array<String>,
    code: Int = 10088
): Boolean {
    permissions.forEach {
        if (isNotGranted(it)) {
            ActivityCompat.requestPermissions(this, permissions, code)
            return false
        }
    }
    return true
}

/**
 * feel free to reset text when listening text
 */
inline fun EditText.onTextChangedProxy(
    crossinline beforeTextChanged: ((
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit) = { _, _, _, _ -> },
    crossinline onTextChanged: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
) {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            removeTextChangedListener(this)
            afterTextChanged(s)
            text?.let { setSelection(if (it.isNotEmpty()) it.length else 1) }
            addTextChangedListener(this)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            removeTextChangedListener(this)
            beforeTextChanged(text, start, count, after)
            addTextChangedListener(this)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            removeTextChangedListener(this)
            onTextChanged(text, start, before, count)
            addTextChangedListener(this)
        }
    }
    addTextChangedListener(textWatcher)
}

/**
 * request full screen before set content view
 */
fun requestFullScreen(window: Window) {
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lp = window.attributes
        // 设置页面延伸到刘海区显示
        lp.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes = lp
    }
}

fun getScreenSize(context: Context): Pair<Int, Int> {
    val resources: Resources = context.resources
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val widthPixels = displayMetrics.widthPixels
    val heightPixels = displayMetrics.heightPixels
    return Pair(widthPixels, heightPixels)
}

fun getScreenSize2(context: Context): Pair<Int, Int> {
    val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val outSize = Point()
    defaultDisplay.getSize(outSize)
    return Pair(outSize.x, outSize.y)
}


/**
 * 获取进程号对应的进程名
 *
 * @param pid 进程号
 * @return 进程名
 */
fun getProcessName(pid: Int): String? {
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
        var processName: String = reader.readLine()
        if (!TextUtils.isEmpty(processName)) {
            processName = processName.trim { it <= ' ' }
        }
        return processName
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
    } finally {
        try {
            reader?.close()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
    return null
}

fun readFromAsset(context: Context, fileName: String): String? {
    var ins: InputStream? = null
    try {
        ins = context.assets.open(fileName)
        val size = ins.available()
        val buffer = ByteArray(size)
        ins.read(buffer)
        return String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        ins?.close()
    }
    return null
}