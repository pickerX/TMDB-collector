package io.tmdb.collector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.tmdb.collector.api.MovieApi
import io.tmdb.collector.repo.MovieRepo
import io.tmdb.collector.repo.UserRepo
import javax.inject.Singleton

/**
 *
 * @author: pickerx
 * @date:2020/11/2 10:36 AM
 */
@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun userRepo(): UserRepo {
        return UserRepo()
    }

    @Singleton
    @Provides
    fun movieRepo(api: MovieApi): MovieRepo {
        return MovieRepo(api)
    }
}
//@Module
//@InstallIn(ActivityRetainedComponent::class)
//abstract class RepositoryModule {
//
//    @Binds
//    abstract fun userRepo(): UserRepo
//
//    @Binds
//    abstract fun movieRepo(api: MovieApi): MovieRepo
//
//}