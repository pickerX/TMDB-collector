package io.tmdb.collector.api

import io.tmdb.collector.data.http.ConfigureData
import retrofit2.Call
import retrofit2.http.GET

/**
 * configure api: such as image, language, countries... configs
 *
 * @author: pickerx
 * @date:2020/12/2 3:31 PM
 */
interface ConfigureApi {

    @GET("/configuration")
    suspend fun fetchConfiguration(): Call<ConfigureData>
}