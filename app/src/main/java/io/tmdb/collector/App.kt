package io.tmdb.collector

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        private var INSTANCE: App? = null

        fun get() = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}