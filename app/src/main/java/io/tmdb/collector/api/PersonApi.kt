package io.tmdb.collector.api

import com.skydoves.sandwich.ApiResponse
import io.tmdb.collector.data.Person
import io.tmdb.collector.data.http.ResultData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author: pickerx
 * @date:2021/3/3 11:18 AM
 */
interface PersonApi {
    /**
     * Get the list of popular people on TMDb. This list updates daily.
     * @param page Specify which page to query. default 1 （0~10000）
     */
    @GET("person/popular")
    suspend fun getPopular(@Query("page") page: Int = 1): ApiResponse<ResultData<Person>>
}