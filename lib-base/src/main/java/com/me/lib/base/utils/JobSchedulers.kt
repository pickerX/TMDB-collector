package com.me.lib.base.utils

import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

/**
 *
 * 要模拟隐式广播和后台服务不可用的条件，请输入以下命令：
 *          $ adb shell cmd appops set <package_name> RUN_IN_BACKGROUND ignore
 *
 * 要重新启用隐式广播和后台服务，请输入以下命令：
 *          $ adb shell cmd appops set <package_name> RUN_IN_BACKGROUND allow
 *
 * @date:2020/10/12 9:27 AM
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class JobSchedulers {
    companion object {
        private const val MY_BACKGROUND_JOB = 0

        /**
         * 在wifi情况下启动作业
         */
        fun <T : Service> start(
            context: Context,
            myJobService: Class<T>,
            requireCharging: Boolean = false
        ) {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val job = JobInfo
                .Builder(MY_BACKGROUND_JOB, ComponentName(context, myJobService))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(requireCharging)
                .build()
            jobScheduler.schedule(job)
        }

        fun cancelAll(context: Context) {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancelAll()
        }
    }

}