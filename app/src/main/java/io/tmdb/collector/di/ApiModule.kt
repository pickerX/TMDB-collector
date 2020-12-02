package io.tmdb.collector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.tmdb.collector.api.MovieApi
import io.tmdb.collector.api.UserApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 * @author: pickerx
 * @date:2020/11/25 11:24 AM
 */
@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun userService(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun movieService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

}