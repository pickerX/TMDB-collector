package io.tmdb.collector.utils

import android.app.ActivityManager
import android.content.Context


/**
 * Memory utils
 * @author: pickerx
 * @date:2020/12/8 2:21 PM
 */

/**
 * 1/8
 */
fun ideaMemorySize(context: Context): Int {
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val availMenInBytes = am.memoryClass * 1024 * 1024
    return availMenInBytes / 8
}