package com.me.lib.base.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {
    companion object {

        fun basic(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ApiResponseConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }

        fun withInterceptor(baseUrl: String, interceptors: Array<Interceptor>): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(interceptors))
        }

        private fun getOkHttpClient(interceptors: Array<Interceptor>? = null): OkHttpClient {
            val builder = OkHttpClient.Builder()
//            okHttpClientBuilder.cookieJar(CookieJarImpl(MemoryCookieStore()))
            builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

            builder.addInterceptor(
                HttpLoggingInterceptor { message -> Log.e("okHttp", ">> $message  ") }
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            interceptors?.let { builder.interceptors().addAll(it) }

            return builder.build()
        }
    }
}