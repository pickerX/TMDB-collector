package io.tmdb.collector.utils

import android.content.Context
import android.os.Process
import com.me.lib.base.utils.getProcessName
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy

/**
 * @param appId 注册时申请的APPID
 */
fun initBuglyReport(context: Context, appId: String) {
// 获取当前包名
    val packageName: String = context.packageName
    // 获取当前进程名
    val processName = getProcessName(Process.myPid())
    // 设置是否为上报进程
    val strategy = UserStrategy(context)
    strategy.isUploadProcess = processName == null || processName == packageName
    // 初始化Bugly debug:false
    CrashReport.initCrashReport(context, appId, false, strategy)
}