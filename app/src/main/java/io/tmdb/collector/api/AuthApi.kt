package io.tmdb.collector.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 账户授权 api
 * @author: pickerx
 * @date:2020/11/16 10:43 AM
 */
interface AuthApi {

    @GET("/authentication/token/new")
    fun token(@Query("apiKey") apiKey: String)
}