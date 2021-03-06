package io.tmdb.collector.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.tmdb.collector.room.AppDatabase
import io.tmdb.collector.room.newRoom
import timber.log.Timber
import javax.inject.Singleton

/**
 *
 * @author: pickerx
 * @date:2020/11/2 10:34 AM
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase {
        return newRoom(context.applicationContext, AppDatabase::class.java, "tmdb.db", {
            Timber.i("database init done!")
        })
    }

}