package io.tmdb.collector.di

import com.me.lib.base.retrofit.RequestInterceptor
import com.me.lib.base.retrofit.RetrofitFactory
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.tmdb.collector.api.Api
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 * @author: pickerx
 * @date:2020/11/2 10:35 AM
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        return RetrofitFactory
            .withInterceptor(Api.v3(), arrayOf(RequestInterceptor()))
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

}