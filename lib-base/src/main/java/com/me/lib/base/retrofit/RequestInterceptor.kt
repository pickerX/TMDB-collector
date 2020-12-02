package com.me.lib.base.retrofit

import android.util.Log
import com.me.lib.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @author: pickerx
 * @date:2020/11/16 11:02 AM
 */
class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .addQueryParameter("language", "en-US")
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}